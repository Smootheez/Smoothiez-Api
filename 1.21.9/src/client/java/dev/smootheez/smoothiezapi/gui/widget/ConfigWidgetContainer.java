package dev.smootheez.smoothiezapi.gui.widget;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.example.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;

@Environment(EnvType.CLIENT)
public class ConfigWidgetContainer extends ContainerObjectSelectionList<ConfigWidgetEntry> {
    public ConfigWidgetContainer(Minecraft minecraft, int i, int j, int k) {
        super(minecraft, i, j, k, 24);
        ExampleConfig config = ConfigManager.getConfig(ExampleConfig.class);
        this.addEntry(new BooleanWidgetEntry(Component.literal("Boolean example"), null, config.getBooleanExample()));
        this.addEntry(new CycleWidgetEntry<>(Component.literal("Cycle example"), null, config.getEnumExample()));

        this.setScrollAmount(this.scrollAmount());
    }

    public boolean isDefaultValue() {
        return this.children().stream()
                .anyMatch(entry -> entry instanceof LabeledWidgetEntry<?> labeled && labeled.isDefaultValue());
    }

    public boolean isModified() {
        return this.children().stream()
                .anyMatch(entry -> entry instanceof LabeledWidgetEntry<?> labeled && labeled.isModified());
    }


    @Override
    public int getRowWidth() {
        if (this.scrollbarVisible()) return this.getWidth() - 20;
        return this.getWidth() - 12;
    }

    @Override
    public int getRowLeft() {
        return 6;
    }

    @Override
    protected int scrollBarX() {
        return this.getRight() - 12;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderWidget(guiGraphics, i, j, f);
        ConfigWidgetEntry widgetEntry = this.getHovered();
        if (widgetEntry != null && widgetEntry.tooltip != null)
            guiGraphics.setTooltipForNextFrame(widgetEntry.tooltip, i, j);
    }
}
