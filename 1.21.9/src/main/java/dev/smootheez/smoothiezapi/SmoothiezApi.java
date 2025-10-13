package dev.smootheez.smoothiezapi;

import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.fabricmc.loader.api.*;

import java.lang.reflect.*;
import java.util.*;

public class SmoothiezApi implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.info("Initializing " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");

        try {
            registerConfigs();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Constants.LOGGER.error("Failed to register configs: {}", e.getMessage());
        }
    }

    private static void registerConfigs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Collection<ConfigApi> configs = FabricLoader.getInstance().getEntrypoints(Constants.MOD_ID, ConfigApi.class);
        for (ConfigApi config : configs) {
            Class<? extends ConfigApi> configClass = config.getClass();
            if (configClass.isAnnotationPresent(Config.class)) ConfigManager.register(configClass.getDeclaredConstructor().newInstance());
        }
    }
}
