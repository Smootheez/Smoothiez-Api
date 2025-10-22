package dev.smootheez.example;

import dev.smootheez.example.util.*;
import net.fabricmc.api.*;

public class Example implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.info("Initializing " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");
    }
}
