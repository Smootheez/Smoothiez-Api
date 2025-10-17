package dev.smootheez.smoothiezapi.gui.widget.entries.container;

import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.widget.base.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ConfigWidgetContainer extends ContainerObjectSelectionList<ConfigWidgetEntry> {
    private final int layoutWidth;
    private final String configId;
    private final List<ConfigOption<?>> allConfigOptions;
    private String filter = "";

    public ConfigWidgetContainer(Minecraft minecraft, HeaderAndFooterLayout layout, ConfigApi configApi) {
        super(minecraft, layout.getWidth(), layout.getContentHeight(), layout.getHeaderHeight(), 24);
        this.layoutWidth = layout.getWidth();
        this.configId = configApi.getConfigId();
        this.allConfigOptions = configApi.getAllConfigOptions();

        refreshEntries();

        this.setScrollAmount(this.scrollAmount());
    }

    public void filter(String search) {
        this.filter = search;
        refreshEntries();
    }

    // TODO: fix translation issue
    private boolean matchesSearchTerm(ConfigOption<?> option, String search) {
        String lowerCaseSearch = search.toLowerCase(Locale.ROOT);

        String translationKey = WidgetHandler.CONFIG_WIDGET + configId + "." + option.getKey();
        String translatedText = I18n.exists(translationKey) ?
                I18n.get(translationKey).toLowerCase(Locale.ROOT) : option.getKey().toLowerCase(Locale.ROOT);
        Constants.LOGGER.info("Does option has translation: {}", I18n.exists(translationKey));

        return translatedText.contains(lowerCaseSearch);
    }

    private void refreshEntries() {
        this.clearEntries();

        this.allConfigOptions.stream().filter(option -> matchesSearchTerm(option, filter))
                .forEach(option -> this.addEntry(createWidget(option)));
    }

    public <T> ConfigWidgetEntry createWidget(ConfigOption<T> option) {
        return option.getWidgetHandler().createWidget(option, configId, createTooltip(option));
    }

    private List<FormattedCharSequence> createTooltip(ConfigOption<?> option) {
        String tooltipKey = WidgetHandler.CONFIG_WIDGET + configId + "." + option.getKey() + ".description";
        Component translatable = Component.translatable(tooltipKey);
        if (I18n.exists(tooltipKey)) {
            return this.minecraft.font.split(translatable, layoutWidth / 2);
        }
        return Collections.emptyList();
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

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderWidget(guiGraphics, i, j, f);
        ConfigWidgetEntry widgetEntry = this.getHovered();
        if (widgetEntry != null && widgetEntry.tooltip != null)
            guiGraphics.setTooltipForNextFrame(widgetEntry.tooltip, i, j);
    }
}
