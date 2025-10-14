package dev.smootheez.smoothiezapi.gui.widget;

import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;

@Environment(EnvType.CLIENT)
public class ExampleListWidget extends ObjectSelectionList<ExampleListWidget.ExampleEntry> {
    public ExampleListWidget(Minecraft minecraft, int i, int j, int k) {
        super(minecraft, i, j, k, 16);
        for (int l = 0; l < 100; l++) {
            this.addEntry(new ExampleEntry(Component.literal("Entry number " + (l + 1) )));
        }

        this.setScrollAmount(this.scrollAmount());
    }

    @Override
    public int getRowWidth() {
        if (this.scrollbarVisible()) return this.getWidth() - 10;
        return this.getWidth() - 4;
    }

    @Override
    public int getRowLeft() {
        return 2;
    }

    @Override
    protected int scrollBarX() {
        return this.getRight() - 6;
    }

    @Environment(EnvType.CLIENT)
    public static class ExampleEntry extends ObjectSelectionList.Entry<ExampleEntry> {
        private final Component label;

        public ExampleEntry(Component label) {
            this.label = label;
        }

        @Override
        public Component getNarration() {
            return this.label;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
            int k = this.getContentX() - 1;
            int l = this.getContentY() - 1;
            int m = this.getContentRight() + 1;
            int n = this.getContentBottom() + 1;
            guiGraphics.fill(k, l, m, n, -8978432);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.label, this.getX() + this.getWidth() / 2, this.getContentYMiddle() - 9 / 2, -1);
        }
    }
}

