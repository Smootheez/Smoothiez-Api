package io.github.smootheez.smoothiezapi;

import io.github.smootheez.smoothiezapi.api.*;
import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.hud.*;
import net.fabricmc.loader.api.*;
import net.minecraft.resources.*;

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

    private static void registerConfigs() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Collection<ConfigApi> configs = FabricLoader.getInstance().getEntrypoints(Constants.MOD_ID, ConfigApi.class);
        for (ConfigApi config : configs) {
            Class<? extends ConfigApi> configClass = config.getClass();
            if (configClass.isAnnotationPresent(Config.class))
                ConfigManager.register(configClass.getDeclaredConstructor().newInstance());
        }
    }
}
