package io.github.smootheez.smoothiezapi.util;

import net.fabricmc.loader.api.*;

/**
 * Utility class for verifying the presence of other mods within a Fabric environment.
 * <p>
 * The {@code ModChecker} is primarily used internally by the Smoothiez API
 * to ensure compatibility and perform conditional integrations — for example,
 * checking if <b>ModMenu</b> or another mod is installed before registering
 * additional features such as configuration screens.
 *
 * <h2>Usage Context</h2>
 * <ul>
 *     <li>Used by {@link ConfigManager#register(ConfigApi)} to verify whether
 *     the target mod (defined by {@link Config#name()}) and <b>ModMenu</b> are both installed
 *     before registering GUI support.</li>
 *     <li>Can be used by other mods or APIs to safely check dependency presence
 *     at runtime without crashing if a mod is missing.</li>
 * </ul>
 *
 * <h3>Example Usage</h3>
 * <pre>{@code
 * if (ModChecker.isModInstalled("modmenu")) {
 *     LOGGER.info("ModMenu detected! Registering Smoothiez API config screen...");
 * }
 * }</pre>
 *
 * <h3>Implementation Details</h3>
 * <ul>
 *     <li>Relies on {@link FabricLoader#getAllMods()} to iterate through all loaded mods.</li>
 *     <li>Performs a case-sensitive comparison against {@link ModContainer#getMetadata()}.</li>
 *     <li>Returns {@code false} for null or empty mod IDs to ensure safe calls.</li>
 * </ul>
 *
 * @see FabricLoader
 * @see ModContainer
 * @see ConfigManager
 * @see Config
 */
public class ModChecker {

    /**
     * Private constructor to prevent instantiation.
     * <p>
     * This class is a pure utility and should only be accessed
     * through its static methods.
     */
    private ModChecker() {}

    /**
     * Checks whether a mod with the given mod ID is currently installed.
     * <p>
     * This method safely handles {@code null} or empty mod IDs by returning {@code false}.
     *
     * @param modId the mod ID to check (e.g., {@code "modmenu"} or {@code "fabric"})
     * @return {@code true} if the mod is installed and loaded; {@code false} otherwise
     */
    public static boolean isModInstalled(String modId) {
        if (modId == null || modId.isEmpty()) return false;
        for (ModContainer mod : FabricLoader.getInstance().getAllMods())
            if (mod.getMetadata().getId().equals(modId)) return true;
        return false;
    }
}

