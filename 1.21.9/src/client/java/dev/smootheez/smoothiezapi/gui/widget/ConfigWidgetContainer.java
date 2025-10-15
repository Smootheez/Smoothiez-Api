package dev.smootheez.smoothiezapi.gui.widget;

import com.google.common.collect.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.*;
import net.minecraft.client.gui.narration.*;
import net.minecraft.network.chat.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ConfigWidgetContainer extends ContainerObjectSelectionList<ConfigWidgetContainer.ConfigWidgetEntry> {
    public ConfigWidgetContainer(Minecraft minecraft, int i, int j, int k) {
        super(minecraft, i, j, k, 24);
        for (int l = 0; l < 100; l++) {
            this.addEntry(new ConfigWidgetEntry(Component.literal("Config Widget Number " + (l + 1) )));
        }

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

    @Environment(EnvType.CLIENT)
    public class ConfigWidgetEntry extends ContainerObjectSelectionList.Entry<ConfigWidgetEntry> {
        protected final List<AbstractWidget> children = Lists.newArrayList();
        protected final Button resetButton;
        private final CycleButton<Boolean> booleanButton;
        private final StringWidget stringWidget;

        public ConfigWidgetEntry(Component label) {
            Font font = ConfigWidgetContainer.this.minecraft.font;
            this.booleanButton = CycleButton.onOffBuilder()
                    .displayOnlyValue()
                    .create(0, 0, 80, 20, Component.literal(""), ((cycleButton, object) -> this.resetButtonAction()));
            this.resetButton = Button.builder(Component.literal("⭮"), button -> this.resetButtonAction()).size(20, 20).build();
            this.stringWidget = new StringWidget(label, font);
            this.stringWidget.setMaxWidth(this.getContentWidth() / 2, StringWidget.TextOverflow.SCROLLING);
            this.children.add(this.resetButton);
            this.children.add(this.stringWidget);
        }

        private void resetButtonAction() {
            // Reset action
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
            int left = this.getContentX();
            int top = this.getContentYMiddle() - 10;

            // Background
            guiGraphics.fill(this.getContentX() - 1, this.getContentY() - 1,
                    this.getContentRight() + 1, this.getContentBottom() + 1, 0x80000000);

            this.resetButton.setPosition(this.getContentRight() - this.resetButton.getWidth(), top);
            this.resetButton.render(guiGraphics, i, j, f);

            this.booleanButton.setPosition(this.getContentRight() - this.resetButton.getWidth() - 3 - this.booleanButton.getWidth(), top);
            this.booleanButton.render(guiGraphics, i, j, f);

            // Draw each wrapped line
            this.stringWidget.setPosition(left + 2, this.getContentYMiddle() - this.stringWidget.getHeight() / 2);
            this.stringWidget.render(guiGraphics, i, j, f);
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
}
