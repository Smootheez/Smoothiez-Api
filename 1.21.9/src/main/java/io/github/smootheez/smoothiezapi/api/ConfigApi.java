package io.github.smootheez.smoothiezapi.api;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.screen.*;
import io.github.smootheez.smoothiezapi.util.*;
import net.minecraft.client.gui.screens.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * The {@code ConfigApi} interface defines the base contract for creating
 * configuration classes in Fabric-based Minecraft mods.
 * <p>
 * A config class implementing this interface must be annotated with {@link Config},
 * and should expose one or more {@link ConfigOption} accessors that represent
 * configurable values such as booleans, integers, or strings.
 * <p>
 * The system uses reflection to automatically:
 * <ul>
 *     <li>Retrieve the configuration ID from the {@link Config} annotation.</li>
 *     <li>Discover all {@link ConfigOption} instances defined in the config class.</li>
 *     <li>Save or load configuration data to the Fabric mod config directory.</li>
 *     <li>Optionally create a {@link ConfigScreen} for in-game editing via ModMenu or similar integrations.</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * @Config(name = "example_mod", autoGui = true)
 * public class ExampleConfig implements ConfigApi {
 *
 *     private final ConfigOption<Boolean> enableParticles =
 *             new ConfigOption<>("enableParticles", true);
 *
 *     public ConfigOption<Boolean> enableParticles() {
 *         return enableParticles;
 *     }
 * }
 *
 * // Load and use:
 * ExampleConfig config = ConfigManager.getConfig(ExampleConfig.class);
 * config.loadConfig();
 * if (config.enableParticles().getValue()) {
 *     // enable particle effects
 * }
 * }</pre>
 *
 * <h3>Integration Notes:</h3>
 * <ul>
 *     <li>Config files are typically stored under {@code .minecraft/config/[modid]/}</li>
 *     <li>Used by helper utilities like {@link ConfigWriter} and {@link ConfigScreen}</li>
 *     <li>Supports reflection-based option discovery for automatic save/load</li>
 * </ul>
 *
 * @see Config
 * @see ConfigOption
 * @see ConfigWriter
 * @see ConfigScreen
 * @author
 *     Smootheez
 */
public interface ConfigApi {

    /**
     * Returns the unique configuration ID defined in the {@link Config} annotation.
     * <p>
     * This ID is used as the file name or folder name for storing config data.
     *
     * @return the config ID specified by {@link Config#name()}
     * @throws IllegalStateException if the implementing class is missing a {@link Config} annotation
     */
    default String getConfigId() {
        Config config = getAnnotation(this.getClass());
        return config.name();
    }

    /**
     * Retrieves the {@link Config} annotation from the given class.
     * <p>
     * Used internally to validate and fetch metadata from the annotated config class.
     *
     * @param clazz the class implementing {@link ConfigApi}
     * @return the {@link Config} annotation instance
     * @throws IllegalStateException if the class is not annotated with {@link Config}
     */
    default Config getAnnotation(Class<? extends ConfigApi> clazz) {
        Config annotation = clazz.getAnnotation(Config.class);
        if (annotation == null)
            throw new IllegalStateException("Config class " + clazz.getName() + " is missing @Config annotation");
        return annotation;
    }

    /**
     * Scans and collects all {@link ConfigOption} instances defined by this configuration class.
     * <p>
     * The method uses reflection to detect public, zero-argument methods returning {@link ConfigOption}.
     * This allows for automatic registration of all configurable fields.
     *
     * @return a list of all discovered {@link ConfigOption} entries
     */
    default List<ConfigOption<?>> getAllConfigOptions() {
        List<ConfigOption<?>> options = new ArrayList<>();

        Arrays.stream(this.getClass().getMethods())
                .filter(method -> ConfigOption.class.isAssignableFrom(method.getReturnType()))
                .filter(method -> method.getParameterCount() == 0)
                .forEach(method -> invokeConfigOption(method, options));

        return options;
    }

    /**
     * Creates a {@link ConfigWriter} for this config instance.
     * <p>
     * The writer handles saving and loading configuration files from
     * the Fabric config directory.
     *
     * @return a configured {@link ConfigWriter} instance
     */
    default ConfigWriter getWriter() {
        ConfigWriter writer = new ConfigWriter(getConfigId());
        getAllConfigOptions().forEach(option -> writer.addOption(option.getKey(), option));
        return writer;
    }

    /**
     * Invokes a reflected {@link ConfigOption}-returning method and adds its
     * result to the provided list if successful.
     * <p>
     * This is an internal helper used by {@link #getAllConfigOptions()}.
     *
     * @param method  the method reference discovered via reflection
     * @param options the list of collected config options
     */
    private void invokeConfigOption(Method method, List<ConfigOption<?>> options) {
        try {
            Object value = method.invoke(this);
            if (value instanceof ConfigOption<?> option) options.add(option);
        } catch (ReflectiveOperationException e) {
            Constants.LOGGER.error("Failed to get config option from method {}: {}", method.getName(), e.getMessage());
        }
    }

    /**
     * Saves this configuration to the mod config directory.
     * <p>
     * Uses {@link ConfigWriter#saveConfig()} under the hood.
     */
    default void saveConfig() {
        getWriter().saveConfig();
    }

    /**
     * Loads this configuration from the mod config directory.
     * <p>
     * Uses {@link ConfigWriter#loadConfig()} to populate all {@link ConfigOption}s
     * with saved values if present.
     */
    default void loadConfig() {
        getWriter().loadConfig();
    }

    /**
     * Creates and returns a Fabric {@link Screen} for editing this configuration in-game.
     * <p>
     * This is commonly used with ModMenu integration, allowing players to
     * configure your mod directly from the Mods menu.
     *
     * @param parent the parent {@link Screen} (usually ModMenu config entry point)
     * @return a {@link ConfigScreen} instance representing this config
     */
    default Screen getScreen(Screen parent) {
        return new ConfigScreen(parent, this);
    }
}

