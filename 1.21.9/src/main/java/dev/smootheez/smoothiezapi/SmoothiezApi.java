package dev.smootheez.smoothiezapi;

import dev.smootheez.smoothiezapi.annotations.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.fabricmc.loader.api.*;

import java.util.*;

public class SmoothiezApi implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.info("Initializing " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");

        registerConfigs();
    }

    private static void registerConfigs() {
        Collection<Object> configs = FabricLoader.getInstance().getEntrypoints(Constants.MOD_ID, Object.class);
        for (Object config : configs) {
            Class<?> configClass = config.getClass();
            if (configClass.isAnnotationPresent(Config.class)) ConfigManager.register(configClass);
        }
    }
}
