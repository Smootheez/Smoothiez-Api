package dev.smootheez.smoothiezapi.gui.screen;

import dev.smootheez.smoothiezapi.gui.widget.*;
import net.fabricmc.api.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private final Screen parent;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 30, 30);
    private OptionListWidgetContainer optionListWidgetContainer;
    private ConfigWidgetContainer configWidgetContainer;
    private Button resetAll;
    private Button saveAndQuit;

    public ConfigScreen(Screen parent) {
        super(Component.literal("Exampe Screen Title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        LinearLayout headerLayout = this.layout.addToHeader(LinearLayout.vertical().spacing(4));
        headerLayout.defaultCellSetting().alignHorizontallyCenter();
        headerLayout.addChild(new StringWidget(this.title, this.font));
        LinearLayout footerLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(4));
        footerLayout.addChild(Button.builder(CommonComponents.GUI_CANCEL,
                btn -> onClose()).size(80, 20).build());
        resetAll = Button.builder(Component.literal("Reset All"),
                btn -> onClose()).size(80, 20).build();
        footerLayout.addChild(resetAll);
        saveAndQuit = Button.builder(Component.literal("Save & Quit"),
                btn -> onClose()).size(80, 20).build();
        footerLayout.addChild(saveAndQuit);

        /*this.optionListWidgetContainer = new OptionListWidgetContainer(
                this.minecraft,
                this.width / 2 - 10,
                this.layout.getContentHeight(),
                this.layout.getHeaderHeight()
        );
        this.addRenderableWidget(optionListWidgetContainer);*/

        this.configWidgetContainer = new ConfigWidgetContainer(
                this.minecraft,
                this.width,
                this.layout.getContentHeight(),
                this.layout.getHeaderHeight()
        );
        this.addRenderableWidget(configWidgetContainer);

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (optionListWidgetContainer != null) {
            this.optionListWidgetContainer.updateSizeAndPosition(this.width / 2 - 10, this.layout.getContentHeight(), 0, this.layout.getHeaderHeight());
        }
        if (configWidgetContainer != null) {
            this.configWidgetContainer.updateSizeAndPosition(this.width, this.layout.getContentHeight(), 0, this.layout.getHeaderHeight());
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.resetAll.active = !configWidgetContainer.isDefaultValue();
        this.saveAndQuit.active = !configWidgetContainer.isModified(); // TODO: Fix this logic because the button didnt get updated
    }

    @Override
    public void onClose() {
        if (minecraft != null) this.minecraft.setScreen(this.parent);
    }
}
