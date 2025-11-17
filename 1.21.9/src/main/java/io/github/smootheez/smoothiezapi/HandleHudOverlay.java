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
        var element1 = new OverlayElement(iconSize, iconSize);
        var element2 = new OverlayElement(iconSize, iconSize);
        var enginge = new ElementLayoutEngine(List.of(element1, element2), ElementAnchor.START, ElementDirection.HORIZONTAL, 3, 5);
        var elements = enginge.layout(0, 0);

        for (int i = 0; i < elements.size(); i++) {
            var positionedElement = elements.get(i);

            if (i == 0) {
                guiGraphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        DUMMY_ICON_1,
                        positionedElement.x(),
                        positionedElement.y(),
                        0, 0,
                        positionedElement.element().width(),
                        positionedElement.element().height(),
                        positionedElement.element().width(),
                        positionedElement.element().height()
                );
            } else if (i == 1) {
                guiGraphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        DUMMY_ICON_2,
                        positionedElement.x(),
                        positionedElement.y(),
                        0, 0,
                        positionedElement.element().width(),
                        positionedElement.element().height(),
                        positionedElement.element().width(),
                        positionedElement.element().height()
                );
            }
        }
    }
}
