package dev.smootheez.smoothiezapi.gui.widget.base;

import com.google.common.collect.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.*;
import net.minecraft.client.gui.narration.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public abstract class LabeledWidgetEntry<T> extends ConfigWidgetEntry {
    protected final ConfigOption<T> option;
    protected final List<AbstractWidget> children = Lists.newArrayList();
    protected final List<AbstractWidget> rightAlignedWidget = new ArrayList<>();
    protected final StringWidget labelWidget;
    private final Button resetButton;

    private static final int H_PADDING = 1;
    private static final int V_CENTER_OFFSET = 0; // tweak if vertically misaligned
    private static final int SPACING = 3;

    protected LabeledWidgetEntry(Component label, @Nullable List<FormattedCharSequence> description, ConfigOption<T> option) {
        super(description);
        this.option = option;

        this.labelWidget = new StringWidget(label, Minecraft.getInstance().font);
        this.resetButton = Button.builder(Component.literal("⭮"),
                button -> this.resetButtonAction()).size(20, 20).build();

        addChild(this.labelWidget);
        addRightAlignedWidget(this.resetButton);

        updateResetButtonState();
    }

    public void setOptionValue(T value) {
        this.option.setValue(value);
    }

    protected void addChild(AbstractWidget widget) {
        this.children.add(widget);
    }

    protected void addRightAlignedWidget(AbstractWidget widget) {
        addChild(widget);
        this.rightAlignedWidget.add(widget);
    }

    public boolean isDefaultValue() {
        T currentValue = this.option.getValue();
        T defaultValue = this.option.getDefaultValue();
        Constants.LOGGER.info("Current value: {}, `Default value: {}", currentValue, defaultValue);
        return currentValue.equals(defaultValue);
    }

    private void resetButtonAction() {
        this.option.setValue(option.getDefaultValue());
        updateWidgetState();
        updateResetButtonState();
    }

    protected void updateResetButtonState() {
        this.resetButton.active = !isDefaultValue();
    }

    protected void updateWidgetState() {
        // Override this when need to update the widget value
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
    public @NotNull List<? extends GuiEventListener> children() {
        return this.children;
    }

    @Override
    public @NotNull List<? extends NarratableEntry> narratables() {
        return this.children;
    }
}
