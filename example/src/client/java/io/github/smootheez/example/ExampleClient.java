package io.github.smootheez.example;

import io.github.smootheez.example.hud.*;
import io.github.smootheez.example.util.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.hud.*;
import net.minecraft.resources.*;

@Environment(EnvType.CLIENT)
public class ExampleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOGGER.info("Initializing Client " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");
        HudElementRegistry.addFirst(
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "overlay"),
                new HandleHudOverlay()
        );
    }
}
