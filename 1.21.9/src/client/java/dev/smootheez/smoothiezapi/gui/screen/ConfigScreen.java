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
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 8 + 9 + 8 + 20 + 4, 60);
    private ExampleListWidget exampleListWidget;

    public ConfigScreen(Screen parent) {
        super(Component.translatable("config.screen.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        LinearLayout headerLayout = this.layout.addToHeader(LinearLayout.vertical().spacing(4));
        headerLayout.defaultCellSetting().alignHorizontallyCenter();
        headerLayout.addChild(new StringWidget(this.title, this.font));
        LinearLayout footerLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footerLayout.addChild(Button.builder(Component.literal("Close"),
                btn -> onClose()).build());

        this.exampleListWidget = new ExampleListWidget(
                this.minecraft,
                this.width / 2 - 10,
                this.layout.getContentHeight(),
                this.layout.getHeaderHeight()
        );
        this.addRenderableWidget(exampleListWidget);

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (exampleListWidget != null) {
            this.exampleListWidget.updateSizeAndPosition(this.width / 2 - 10, this.layout.getContentHeight(), 0, this.layout.getHeaderHeight());
        }
    }

    @Override
    public void onClose() {
        if (minecraft != null) this.minecraft.setScreen(this.parent);
    }
}
