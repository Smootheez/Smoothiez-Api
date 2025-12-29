package io.github.smootheez.smoothiezapi.gui.screen;

import io.github.smootheez.smoothiezapi.api.*;
import io.github.smootheez.smoothiezapi.gui.widget.entries.container.*;
import net.fabricmc.api.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final ConfigApi configApi;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 50, 30);
    private ConfigWidgetContainer configWidgetContainer;
    private EditBox searchBox;

    public ConfigScreen(Screen parent, ConfigApi configApi) {
        super(Component.translatable("config.screen." + configApi.getConfigId() + ".title"));
        this.parent = parent;
        this.configApi = configApi;
    }

    @Override
    protected void init() {
        this.configWidgetContainer = new ConfigWidgetContainer(
                this.minecraft,
                this.layout,
                configApi
        );

        LinearLayout headerLayout = this.layout.addToHeader(LinearLayout.vertical().spacing(4));
        headerLayout.defaultCellSetting().alignHorizontallyCenter();
        headerLayout.addChild(new StringWidget(this.title, this.font));
        searchBox = new EditBox(this.font, this.layout.getWidth() / 2 - 100, 22, 200, 20, Component.literal(""));
        searchBox.setResponder(configWidgetContainer::filter);
        headerLayout.addChild(searchBox);

        LinearLayout footerLayout = this.layout.addToFooter(LinearLayout.horizontal());
        footerLayout.addChild(Button.builder(CommonComponents.GUI_DONE, btn -> onClose()).build());

        this.addRenderableWidget(configWidgetContainer);

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    @Override
    protected void setInitialFocus() {
        if (this.searchBox != null)
            this.setInitialFocus(this.searchBox);
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (configWidgetContainer != null)
            this.configWidgetContainer.updateSizeAndPosition(this.layout.getWidth(), this.layout.getContentHeight(), 0, this.layout.getHeaderHeight());
    }

    @Override
    public void onClose() {
        if (minecraft != null) {
            this.configApi.saveConfig();
            this.minecraft.setScreen(this.parent);
        }
    }
}
