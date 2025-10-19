package dev.smootheez.smoothiezapi.keymapping;

import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;

import java.util.*;

/**
 * Utility interface for creating and registering new {@link KeyMapping.Category} instances
 * in a Fabric-based Minecraft mod environment.
 * <p>
 * This helper simplifies the process of defining custom keybinding categories
 * by automatically creating a namespaced {@link ResourceLocation} based on the
 * provided mod ID and category ID.
 * <p>
 * Intended for use on the <b>client side</b> only.
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * public static final KeyMapping.Category CATEGORY =
 *         KeyMapCategoryHelper.register("examplemod", "controls");
 * }</pre>
 *
 * <h3>Behavior</h3>
 * <ul>
 *     <li>Requires both {@code modid} and {@code id} to be non-null.</li>
 *     <li>Registers the category via {@link KeyMapping.Category#register(ResourceLocation)}.</li>
 * </ul>
 *
 * @see KeyMapping
 * @see KeyMapping.Category
 */
@Environment(EnvType.CLIENT)
public interface KeyMapCategoryHelper {

    /**
     * Registers a new {@link KeyMapping.Category} with the given mod ID and category ID.
     *
     * @param modid the mod ID (namespace)
     * @param id    the category identifier (path)
     * @return the newly registered {@link KeyMapping.Category}
     * @throws NullPointerException if either argument is {@code null}
     */
    static KeyMapping.Category register(String modid, String id) {
        Objects.requireNonNull(modid, "Mod id cannot be null!");
        Objects.requireNonNull(id, "Id cannot be null!");
        return KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(modid, id));
    }
}

