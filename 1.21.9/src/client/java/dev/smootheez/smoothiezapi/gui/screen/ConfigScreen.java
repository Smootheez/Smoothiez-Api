package dev.smootheez.smoothiezapi.gui.screen;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.example.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.container.*;
import net.fabricmc.api.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final String configId;
    private final List<ConfigOption<?>> options;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 30, 30);
    private ConfigWidgetContainer configWidgetContainer;

    public ConfigScreen(Screen parent, String configId, List<ConfigOption<?>> options) {
        super(Component.translatable("config.screen." + configId + ".title"));
        this.parent = parent;
        this.configId = configId;
        this.options = options;
    }

    @Override
    protected void init() {
        this.layout.addTitleHeader(this.title, this.font);
        LinearLayout headerLayout = this.layout.addToHeader(LinearLayout.vertical().spacing(4));
        headerLayout.defaultCellSetting().alignHorizontallyCenter();
        LinearLayout footerLayout = this.layout.addToFooter(LinearLayout.horizontal());
        footerLayout.addChild(Button.builder(CommonComponents.GUI_DONE,
                btn -> onClose()).build());

        this.configWidgetContainer = new ConfigWidgetContainer(
                this.minecraft,
                this.layout,
                configId,
                options
        );
        this.addRenderableWidget(configWidgetContainer);

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (configWidgetContainer != null)
            this.configWidgetContainer.updateSizeAndPosition(this.layout.getWidth(), this.layout.getContentHeight(), 0, this.layout.getHeaderHeight());
    }

    @Override
    public void onClose() {
        if (minecraft != null) this.minecraft.setScreen(this.parent);
    }
}
