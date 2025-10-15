package dev.smootheez.smoothiezapi.gui.widget;

import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class OptionListWidgetContainer extends ObjectSelectionList<OptionListWidgetContainer.Entry> {
    public OptionListWidgetContainer(Minecraft minecraft, int i, int j, int k) {
        super(minecraft, i, j, k, 18);
        for (int l = 0; l < 100; l++) {
            this.addEntry(new Entry(Component.literal("Entry Widget Number " + (l + 1) )));
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
    public static class Entry extends ObjectSelectionList.Entry<Entry> {
        private final Component label;
        private final Font font = Minecraft.getInstance().font;

        public Entry(Component label) {
            this.label = label;
        }

        @Override
        public @NotNull Component getNarration() {
            return this.label;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
            int left = this.getContentX() + 8;
            int top = this.getContentY() + 3;
            List<FormattedCharSequence> labels = font.split(label, this.getContentWidth() - 16);

            // Background
            guiGraphics.fill(this.getContentX() - 1, this.getContentY() - 1,
                    this.getContentRight() + 1, this.getContentBottom() + 1, 0x80000000);

            // Draw each wrapped line
            guiGraphics.drawString(font, labels.getFirst(), left, top, -1);
        }
    }
}

