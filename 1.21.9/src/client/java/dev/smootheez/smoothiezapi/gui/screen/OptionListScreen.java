package dev.smootheez.smoothiezapi.gui.screen;

import com.google.common.collect.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.*;
import net.fabricmc.api.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.*;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.gui.narration.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class OptionListScreen extends Screen {
    private final Screen parent;
    private final OptionListWidgetEntry widgetEntry;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 30, 30);
    private WidgetContainer widgetContainer;

    public OptionListScreen(Screen parent, OptionListWidgetEntry widgetEntry) {
        super(Component.literal("Edit Option List"));
        this.parent = parent;
        this.widgetEntry = widgetEntry;
    }

    @Override
    protected void init() {
        this.layout.addTitleHeader(this.title, this.font);

        LinearLayout footerLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(4));
        footerLayout.addChild(Button.builder(CommonComponents.GUI_DONE,
                btn -> onClose()).build());
        footerLayout.addChild(Button.builder(Component.literal("Add Value"),
                btn -> {
                    if (this.minecraft != null)
                        this.minecraft.setScreen(new EditOrAddValueScreen(OptionListScreen.this, ScreenActionType.ADD, ""));
                }).build());

        this.widgetContainer = this.addRenderableWidget(new WidgetContainer());

        this.layout.visitWidgets(this::addRenderableWidget);
        repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.widgetContainer != null)
            this.widgetContainer.updateSizeAndPosition(this.layout.getWidth(), this.layout.getContentHeight(), 0, this.layout.getHeaderHeight());
    }

    @Override
    public void onClose() {
        if (minecraft != null) this.minecraft.setScreen(this.parent);
    }

    private class WidgetContainer extends ContainerObjectSelectionList<WidgetEntry> {
        private final ConfigOption<OptionList> option;

        public WidgetContainer() {
            super(OptionListScreen.this.minecraft, layout.getWidth(), layout.getContentHeight(), layout.getHeaderHeight(), 24);
            this.option = widgetEntry.getOption();

            refreshEntries();

            this.setScrollAmount(this.scrollAmount());
        }

        public void removeValue(String value) {
            OptionList optionList = option.getValue();
            optionList.remove(value);
            widgetEntry.setOptionValue(optionList);
            refreshEntries();
        }

        private void refreshEntries() {
            this.clearEntries();
            option.getValue().values().forEach(value -> this.addEntry(new WidgetEntry(value)));
        }

        @Override
        public int getRowWidth() {
            return this.scrollbarVisible() ? this.getWidth() - 20 : this.getWidth() - 12;
        }

        @Override
        public int getRowLeft() {
            return 6;
        }

        @Override
        protected int scrollBarX() {
            return this.getRight() - 12;
        }
    }

    private class WidgetEntry extends ContainerObjectSelectionList.Entry<WidgetEntry> {
        private final StringWidget labelWidget;
        protected final List<AbstractWidget> children = Lists.newArrayList();
        protected final List<AbstractWidget> rightAlignedWidget = new ArrayList<>();

        private static final int H_PADDING = 1;
        private static final int V_CENTER_OFFSET = 0; // tweak if vertically misaligned
        private static final int SPACING = 3;
        private final String label;

        public WidgetEntry(String label) {
            this.label = label;
            this.labelWidget = new StringWidget(Component.literal(this.label), OptionListScreen.this.font);

            addChild(this.labelWidget);
            addRightAlignedWidget(Button.builder(Component.literal("❌"),
                    button -> OptionListScreen.this.widgetContainer.removeValue(label)).size(20, 20).build());
            addRightAlignedWidget(Button.builder(Component.literal("Edit"),
                    this::onPress).size(80, 20).build());
        }

        protected void addChild(AbstractWidget widget) {
            this.children.add(widget);
        }

        protected void addRightAlignedWidget(AbstractWidget widget) {
            addChild(widget);
            this.rightAlignedWidget.add(widget);
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
            int contentX = getContentX();
            guiGraphics.fill(
                    contentX - 1, getContentY() - 1,
                    getContentRight() + 1, getContentBottom() + 1,
                    0x80000000
            );

            int leftBoundary = layoutRightAlignedWidgets(rightAlignedWidget);

            // Label remains anchored to the left
            this.labelWidget.setMaxWidth(leftBoundary - contentX - H_PADDING, StringWidget.TextOverflow.SCROLLING);
            this.labelWidget.setPosition(contentX + H_PADDING + 2, getContentYMiddle() - this.labelWidget.getHeight() / 2);

            // Render
            this.labelWidget.render(guiGraphics, mouseX, mouseY, delta);
            for (AbstractWidget widget : rightAlignedWidget) {
                widget.render(guiGraphics, mouseX, mouseY, delta);
            }
        }

        private int layoutRightAlignedWidgets(List<? extends AbstractWidget> widgets) {
            int contentRight = this.getContentRight() - H_PADDING;
            int centerY = this.getContentYMiddle() + V_CENTER_OFFSET;

            int nextX = contentRight;

            // Position widgets from right to left
            for (AbstractWidget widget : widgets) {
                nextX -= widget.getWidth();
                widget.setPosition(nextX, centerY - widget.getHeight() / 2);
                nextX -= SPACING;
            }

            // Return the x-position where left-side elements (like label) can stop
            return nextX;
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return this.children;
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return this.children;
        }

        private void onPress(Button button) {
            if (OptionListScreen.this.minecraft != null)
                OptionListScreen.this.minecraft.setScreen(new EditOrAddValueScreen(OptionListScreen.this, ScreenActionType.EDIT, this.label));
        }
    }

    private class EditOrAddValueScreen extends Screen {
        private final Screen parent;
        private final String label;
        private OptionList optionList;
        private final ScreenActionType type;
        private EditBox editBox;

        protected EditOrAddValueScreen(Screen parent, ScreenActionType type, String label) {
            super(Component.literal(type.getString()));
            this.parent = parent;
            this.label = label;
            this.type = type;
        }

        @Override
        protected void init() {
            optionList = widgetEntry.getOption().getValue();

            editBox = new EditBox(this.font, this.width / 2 - 75, this.height / 2 - 50, 150, 20, Component.literal(""));
            editBox.setValue(this.label);
            this.addRenderableWidget(editBox);

            Button doneButton = Button.builder(CommonComponents.GUI_DONE,
                    btn -> updateOptionValue()).build();
            doneButton.setPosition(this.width / 2 - doneButton.getWidth() / 2, this.height / 2 - 12);
            this.addRenderableWidget(doneButton);

            Button cancelButton = Button.builder(CommonComponents.GUI_CANCEL,
                    btn -> onClose()).build();
            cancelButton.setPosition(this.width / 2 - cancelButton.getWidth() / 2, this.height / 2 + 12);
            this.addRenderableWidget(cancelButton);
        }

        private void updateOptionValue() {
            String newValue = this.editBox.getValue().trim();
            if (newValue.isEmpty() || newValue.equals(this.label)) {
                onClose();
                return;
            }

            switch (type) {
                case EDIT -> optionList.edit(this.label, newValue);
                case ADD -> optionList.add(newValue);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }

            widgetEntry.setOptionValue(optionList);
            widgetContainer.refreshEntries();
            onClose();
        }

        @Override
        public void onClose() {
            if (minecraft != null) {
                this.minecraft.setScreen(this.parent);
                widgetContainer.refreshEntries();
            }
        }
    }

    // TODO: For now use literal string, change it to translatable later
    private enum ScreenActionType {
        EDIT("Edit Value"),
        ADD("Add Value");

        private final String string;

        ScreenActionType(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }
    }
}
