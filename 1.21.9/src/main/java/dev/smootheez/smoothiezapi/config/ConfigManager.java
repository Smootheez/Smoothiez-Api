package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.util.*;

import java.util.*;

public final class ConfigManager {
    private static final Map<Class<?>, Object> configs = new HashMap<>();

    private ConfigManager() {}

    public static void register(Object configInstance) {
        Objects.requireNonNull(configInstance, "Config instance cannot be null");

        if (!(configInstance instanceof ConfigApi)) {
            Constants.LOGGER.error("Config instance must implement ConfigApi. Class: {} skipping...", configInstance.getClass().getName());
            return;
        }

        configs.putIfAbsent(configInstance.getClass(), configInstance);
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
