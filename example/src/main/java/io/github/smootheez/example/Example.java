package io.github.smootheez.example;

import io.github.smootheez.example.util.*;
import net.fabricmc.api.*;

public class Example implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.info("Initializing " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");
    }
}
