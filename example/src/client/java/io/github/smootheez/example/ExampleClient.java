package io.github.smootheez.example;

import io.github.smootheez.example.util.*;
import net.fabricmc.api.*;

@Environment(EnvType.CLIENT)
public class ExampleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOGGER.info("Initializing Client " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");
    }
}
