package dev.smootheez.smoothiezapi.modmenu;

import com.terraformersmc.modmenu.api.*;
import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.example.*;
import dev.smootheez.smoothiezapi.gui.screen.*;
import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.minecraft.client.gui.screens.*;

import java.util.*;

/**
 * The {@code ModMenuApiImpl} class provides integration between the Smoothiez API
 * configuration system and the ModMenu mod.
 * <p>
 * It implements {@link ModMenuApi} to supply dynamically generated configuration
 * screens for registered {@link ConfigApi} instances, allowing players to modify
 * configuration values directly through ModMenu’s in-game interface.
 *
 * <h2>Overview</h2>
 * <ul>
 *     <li>Acts as a central registry for config screen factories, keyed by mod ID.</li>
 *     <li>Receives registration calls from {@link ConfigManager} when a config has
 *     {@link Config#autoGui()} enabled and ModMenu is detected.</li>
 *     <li>Exposes all registered config screens to ModMenu through
 *     {@link #getProvidedConfigScreenFactories()}.</li>
 * </ul>
 *
 * <h2>Automatic Integration Example</h2>
 * When a configuration is registered and {@link Config#autoGui()} is {@code true},
 * {@link ConfigManager} will automatically call:
 * <pre>{@code
 * ModMenuApiImpl.registerConfigScreen("examplemod", configInstance::getScreen);
 * }</pre>
 *
 * Once registered, the configuration will appear under the target mod’s entry
 * in ModMenu’s configuration list, allowing users to edit settings through a
 * GUI built by {@link ConfigScreen}.
 *
 * <h3>Manual Usage (Advanced)</h3>
 * While automatic registration is handled internally, advanced users may also
 * manually register additional screens using:
 * <pre>{@code
 * ModMenuApiImpl.registerConfigScreen("your_mod_id", parent -> new CustomConfigScreen(parent));
 * }</pre>
 *
 * <h3>Integration Flow</h3>
 * <ol>
 *     <li>{@link ConfigManager#register(ConfigApi)} checks {@link Config#autoGui()}.</li>
 *     <li>If enabled and ModMenu is installed, it calls
 *     {@link #registerConfigScreen(String, ConfigScreenFactory)}.</li>
 *     <li>ModMenu later requests all factories via
 *     {@link #getProvidedConfigScreenFactories()}.</li>
 * </ol>
 *
 * @see Config
 * @see ConfigManager
 * @see ConfigApi
 * @see ConfigScreen
 * @see ModChecker
 */
@Environment(EnvType.CLIENT)
public class ModMenuApiImpl implements ModMenuApi {

    /**
     * Stores all registered ModMenu config screen factories.
     * <p>
     * Each entry maps a mod ID to its associated {@link ConfigScreenFactory},
     * which produces the corresponding {@link Screen}
     * instance when ModMenu requests it.
     */
    private static final Map<String, ConfigScreenFactory<?>> SCREEN_FACTORY = new HashMap<>();

    /**
     * Registers a configuration screen factory for the specified mod.
     * <p>
     * This method is typically called automatically from
     * {@link ConfigManager#register(ConfigApi)} when a config has
     * {@link Config#autoGui()} enabled and ModMenu is present.
     *
     * @param modId   the mod ID associated with the config
     * @param factory the screen factory responsible for creating config screens
     */
    public static void registerConfigScreen(String modId, ConfigScreenFactory<?> factory) {
        SCREEN_FACTORY.putIfAbsent(modId, factory);
    }

    /**
     * Provides a default config screen factory for ModMenu.
     * <p>
     * This default implementation returns the configuration screen
     * for {@link ExampleConfig}, and can be customized or extended
     * as needed.
     *
     * @return a factory that builds a config screen for {@link ExampleConfig}
     */
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> ConfigManager.getConfig(ExampleConfig.class).getScreen(screen);
    }

    /**
     * Returns all registered configuration screen factories for ModMenu.
     * <p>
     * ModMenu calls this method to populate its settings screen list
     * with entries for all mods that have Smoothiez API configs
     * registered via {@link #registerConfigScreen(String, ConfigScreenFactory)}.
     *
     * @return a map of mod IDs to their corresponding config screen factories
     */
    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return SCREEN_FACTORY;
    }
}

