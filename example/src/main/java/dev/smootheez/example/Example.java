package dev.smootheez.example;

import dev.smootheez.example.util.*;
import net.fabricmc.api.*;
import net.fabricmc.loader.api.*;

import java.util.*;

public class Example implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.info("Initializing " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(Constants.MOD_ID);
        modContainer.ifPresent(mod -> Constants.LOGGER.info("Path: {}", mod.getRootPaths()));
    }
}
