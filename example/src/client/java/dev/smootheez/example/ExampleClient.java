package dev.smootheez.example;

import dev.smootheez.example.util.*;
import net.fabricmc.api.*;

public class ExampleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOGGER.info("Initializing Client " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");
    }
}
