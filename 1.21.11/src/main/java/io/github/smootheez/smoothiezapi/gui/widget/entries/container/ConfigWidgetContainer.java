package io.github.smootheez.smoothiezapi.gui.widget.entries.container;

import io.github.smootheez.smoothiezapi.api.*;
import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.widget.base.*;
import io.github.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;

import java.util.*;

import static io.github.smootheez.smoothiezapi.gui.helper.ConfigTranslationHelper.*;

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

    /* ---------------------- Filtering ---------------------- */

    public void filter(String search) {
        this.filter = search == null ? "" : search.trim();
        refreshEntries();
    }

    private void refreshEntries() {
        this.clearEntries();

        allConfigOptions.stream()
                .filter(option -> matchesSearchTerm(option, filter))
                .forEach(option -> addEntry(createWidget(option)));

        this.setScrollAmount(0);
    }

    private boolean matchesSearchTerm(ConfigOption<?> option, String search) {
        if (search.isEmpty()) return true;

        String query = search.toLowerCase(Locale.ROOT);
        String translationKey = getTranslationKey(configId, option.getKey());

        // Prefer localized text if it exists
        String displayText = I18n.exists(translationKey)
                ? I18n.get(translationKey).toLowerCase(Locale.ROOT)
                : option.getKey().toLowerCase(Locale.ROOT);

        return displayText.contains(query);
    }

    /* ---------------------- Widget Creation ---------------------- */

    public <T> ConfigWidgetEntry createWidget(ConfigOption<T> option) {
        DebugMode.sendLogInfo("Creating widget for option: " + option.getKey() + " with type: " + option.getType().getSimpleName());
        return option.getWidgetHandler().createWidget(option, configId, createTooltip(option));
    }

    private List<FormattedCharSequence> createTooltip(ConfigOption<?> option) {
        String tooltipKey = getDescriptionTranslationKey(configId, option.getKey());

        if (!I18n.exists(tooltipKey)) return Collections.emptyList();

        Component tooltipComponent = Component.translatable(tooltipKey);
        return this.minecraft.font.split(tooltipComponent, layoutWidth / 2);
    }

    /* ---------------------- Layout Overrides ---------------------- */

    @Override
    public int getRowWidth() {
        return this.getWidth() - (this.scrollbarVisible() ? 20 : 12);
    }

    @Override
    public int getRowLeft() {
        return 6;
    }

    @Override
    protected int scrollBarX() {
        return this.getRight() - 12;
    }

    /* ---------------------- Rendering ---------------------- */

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);

        ConfigWidgetEntry hovered = this.getHovered();
        if (hovered != null && hovered.tooltip != null) {
            guiGraphics.setTooltipForNextFrame(hovered.tooltip, mouseX, mouseY);
        }
    }
}

