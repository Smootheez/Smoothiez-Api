package dev.smootheez.smoothiezapi.gui.screen;

import com.google.common.collect.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.*;
import dev.smootheez.smoothiezapi.util.*;
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

    public static final String SCREEN = "screen.";
    /* ------------------------------------------------------------
     * Fields
     * ------------------------------------------------------------ */
    private final Screen parent;
    private final OptionListWidgetEntry widgetEntry;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 30, 30);
    private WidgetContainer widgetContainer;

    /* ------------------------------------------------------------
     * Constructor
     * ------------------------------------------------------------ */
    public OptionListScreen(Screen parent, OptionListWidgetEntry widgetEntry) {
        super(Component.translatable(SCREEN + Constants.MOD_ID + ".option_list.title"));
        this.parent = parent;
        this.widgetEntry = widgetEntry;
    }

    /* ------------------------------------------------------------
     * Screen lifecycle methods
     * ------------------------------------------------------------ */
    @Override
    protected void init() {
        // Title
        this.layout.addTitleHeader(this.title, this.font);

        // Footer buttons
        LinearLayout footerLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(4));
        footerLayout.addChild(Button.builder(CommonComponents.GUI_DONE, btn -> onClose()).build());
        footerLayout.addChild(Button.builder(Component.translatable("widget." + Constants.MOD_ID + ".add.option_list"),
                btn -> {
                    if (this.minecraft != null) {
                        this.minecraft.setScreen(new EditOrAddValueScreen(this, ScreenActionType.ADD, ""));
                    }
                }).build());

        // List container
        this.widgetContainer = this.addRenderableWidget(new WidgetContainer());

        // Final layout setup
        this.layout.visitWidgets(this::addRenderableWidget);
        repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.widgetContainer != null) {
            this.widgetContainer.updateSizeAndPosition(
                    this.layout.getWidth(),
                    this.layout.getContentHeight(),
                    0,
                    this.layout.getHeaderHeight()
            );
        }
    }

    @Override
    public void onClose() {
        if (minecraft != null) this.minecraft.setScreen(this.parent);
    }

    /* ------------------------------------------------------------
     * Inner class: WidgetContainer (List container)
     * ------------------------------------------------------------ */
    @Environment(EnvType.CLIENT)
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

    /* ------------------------------------------------------------
     * Inner class: WidgetEntry (Single row entry)
     * ------------------------------------------------------------ */
    @Environment(EnvType.CLIENT)
    private class WidgetEntry extends ContainerObjectSelectionList.Entry<WidgetEntry> {
        private final String label;
        private final StringWidget labelWidget;

        protected final List<AbstractWidget> children = Lists.newArrayList();
        protected final List<AbstractWidget> rightAlignedWidget = new ArrayList<>();

        private static final int H_PADDING = 1;
        private static final int V_CENTER_OFFSET = 0; // tweak if vertically misaligned
        private static final int SPACING = 3;

        public WidgetEntry(String label) {
            this.label = label;
            this.labelWidget = new StringWidget(Component.literal(this.label), OptionListScreen.this.font);

            addChild(this.labelWidget);
            addRightAlignedWidget(Button.builder(Component.literal("❌"),
                            button -> OptionListScreen.this.widgetContainer.removeValue(label))
                    .size(20, 20)
                    .build());
            addRightAlignedWidget(Button.builder(Component.translatable("widget." + Constants.MOD_ID + ".edit.option_list"), this::onPress)
                    .size(80, 20)
                    .build());
        }

        /* ---------- Layout Helpers ---------- */
        protected void addChild(AbstractWidget widget) {
            this.children.add(widget);
        }

        protected void addRightAlignedWidget(AbstractWidget widget) {
            addChild(widget);
            this.rightAlignedWidget.add(widget);
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

            return nextX; // left limit for label text
        }

        /* ---------- Rendering ---------- */
        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
            int contentX = getContentX();

            // Background highlight
            guiGraphics.fill(
                    contentX - 1, getContentY() - 1,
                    getContentRight() + 1, getContentBottom() + 1,
                    0x80000000
            );

            int leftBoundary = layoutRightAlignedWidgets(rightAlignedWidget);

            // Label anchored left
            this.labelWidget.setMaxWidth(leftBoundary - contentX - H_PADDING, StringWidget.TextOverflow.SCROLLING);
            this.labelWidget.setPosition(contentX + H_PADDING + 2, getContentYMiddle() - this.labelWidget.getHeight() / 2);

            // Draw label + buttons
            this.labelWidget.render(guiGraphics, mouseX, mouseY, delta);
            for (AbstractWidget widget : rightAlignedWidget) {
                widget.render(guiGraphics, mouseX, mouseY, delta);
            }
        }

        /* ---------- Event & Narration ---------- */
        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return this.children;
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return this.children;
        }

        private void onPress(Button button) {
            if (OptionListScreen.this.minecraft != null) {
                OptionListScreen.this.minecraft.setScreen(
                        new EditOrAddValueScreen(OptionListScreen.this, ScreenActionType.EDIT, this.label)
                );
            }
        }
    }

    /* ------------------------------------------------------------
     * Inner class: EditOrAddValueScreen
     * ------------------------------------------------------------ */
    @Environment(EnvType.CLIENT)
    private class EditOrAddValueScreen extends Screen {
        private final Screen parent;
        private final String label;
        private final ScreenActionType type;
        private OptionList optionList;
        private EditBox editBox;

        protected EditOrAddValueScreen(Screen parent, ScreenActionType type, String label) {
            super(type.getComponent());
            this.parent = parent;
            this.label = label;
            this.type = type;
        }

        @Override
        protected void init() {
            optionList = widgetEntry.getOption().getValue();

            // Input box
            editBox = new EditBox(this.font, this.width / 2 - 75, this.height / 2 - 50, 150, 20, Component.literal(""));
            editBox.setValue(this.label);
            this.addRenderableWidget(editBox);

            // Done button
            Button doneButton = Button.builder(CommonComponents.GUI_DONE, btn -> updateOptionValue()).build();
            doneButton.setPosition(this.width / 2 - doneButton.getWidth() / 2, this.height / 2 - 12);
            this.addRenderableWidget(doneButton);

            // Cancel button
            Button cancelButton = Button.builder(CommonComponents.GUI_CANCEL, btn -> onClose()).build();
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

    /* ------------------------------------------------------------
     * Enum: ScreenActionType
     * ------------------------------------------------------------ */
    @Environment(EnvType.CLIENT)
    private enum ScreenActionType {
        EDIT(Component.translatable(SCREEN + Constants.MOD_ID + ".edit.title")),
        ADD(Component.translatable(SCREEN + Constants.MOD_ID + ".add.title"));

        private final Component component;

        ScreenActionType(Component component) {
            this.component = component;
        }

        public Component getComponent() {
            return component;
        }
    }
}
