package io.github.smootheez.smoothiezapi;

import io.github.smootheez.smoothiezapi.gui.overlay.*;
import net.fabricmc.fabric.api.client.rendering.v1.hud.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;

import java.util.*;

public class HandleHudOverlay implements HudElement {
    private static final ResourceLocation DUMMY_ICON_1 =
            ResourceLocation.withDefaultNamespace("textures/item/elytra.png");
    private static final ResourceLocation DUMMY_ICON_2 =
            ResourceLocation.withDefaultNamespace("textures/item/diamond.png");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        var iconSize = 16;
        var element1 = new OverlayElement(
                iconSize, iconSize,
                (g, x, y) -> g.blit(
                        RenderPipelines.GUI_TEXTURED,
                        DUMMY_ICON_1,
                        x, y,
                        0, 0,
                        iconSize, iconSize, iconSize, iconSize
                )
        );
        var element2 = new OverlayElement(
                iconSize, iconSize,
                (g, x, y) -> g.blit(
                        RenderPipelines.GUI_TEXTURED,
                        DUMMY_ICON_2,
                        x, y,
                        0, 0,
                        iconSize, iconSize, iconSize, iconSize
                )
        );
        var enginge = new ElementLayoutEngine(
                List.of(element1, element2), ElementAnchor.START, ElementDirection.VERTICAL, 0, 0
        );
        var elements = enginge.layout(0, 0);

        enginge.renderLayout(guiGraphics, elements);
    }
}
