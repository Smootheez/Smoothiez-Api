package io.github.smootheez.smoothiezapi.gui.overlay;

import net.minecraft.client.gui.*;

@FunctionalInterface
public interface ElementRenderer {
    void render(GuiGraphics g, int x, int y);
}
