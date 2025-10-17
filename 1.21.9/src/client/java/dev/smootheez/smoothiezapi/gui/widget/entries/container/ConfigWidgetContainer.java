package dev.smootheez.smoothiezapi.gui.widget.entries.container;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.example.*;
import dev.smootheez.smoothiezapi.gui.widget.base.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.network.chat.*;

@Environment(EnvType.CLIENT)
public class ConfigWidgetContainer extends ContainerObjectSelectionList<ConfigWidgetEntry> {
    public ConfigWidgetContainer(Minecraft minecraft, HeaderAndFooterLayout layout) {
        super(minecraft, layout.getWidth(), layout.getContentHeight(), layout.getHeaderHeight(), 24);
        ExampleConfig config = ConfigManager.getConfig(ExampleConfig.class);
        this.addEntry(new BooleanWidgetEntry(Component.literal("Boolean example"), null, config.getBooleanExample()));
        this.addEntry(new CycleWidgetEntry<>(Component.literal("Cycle example"), null, config.getEnumExample()));
        this.addEntry(new TextIntegerWidgetEntry(Component.literal("Integer example"), null, config.getIntegerExample()));
        this.addEntry(new TextDoubleWidgetEntry(Component.literal("Double example"), null, config.getDoubleExample()));
        this.addEntry(new TextStringWidgetEntry(Component.literal("String example"), null, config.getStringExample()));
        this.addEntry(new OptionListWidgetEntry(Component.literal("Option List example"), null, config.getOptionListExample()));

        this.setScrollAmount(this.scrollAmount());
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
