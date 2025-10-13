package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.util.*;

import java.util.*;

public final class ConfigManager {
    private static final Map<Class<?>, Object> configs = new HashMap<>();

    private ConfigManager() {}

    public static void register(ConfigApi configInstance) {
        Objects.requireNonNull(configInstance, "Config instance cannot be null");

        Class<?> instanceClass = configInstance.getClass();

        configs.putIfAbsent(instanceClass, configInstance);

        // Automatically load existing config from disk (if exists)
        configInstance.loadConfig();

        // Then save (to ensure file is created and synced)
        configInstance.saveConfig();

        Constants.LOGGER.info("Registered config: {}", instanceClass.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getConfig(Class<T> configClass) {
        return (T) configs.get(configClass);
    }

    public static boolean isRegistered(Class<?> configClass) {
        return configs.containsKey(configClass);
    }

    public static void clearConfigs() {
        configs.clear();
    }
}
