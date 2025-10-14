package dev.smootheez.smoothiezapi.keymapping;

import net.minecraft.client.*;
import net.minecraft.resources.*;

import java.util.*;

public interface KeyMapCategoryHelper {
    /**
     * Registers a new key mapping category.
     *
     * @param modid The mod ID.
     * @param id The ID of the category within the mod.
     * @return The registered {@link KeyMapping.Category}.
     * @throws NullPointerException if {@code modid} or {@code id} is null.
     */
    static KeyMapping.Category register(String modid, String id) {
        Objects.requireNonNull(modid, "Mod id cannot be null!");
        Objects.requireNonNull(id, "Id cannot be null!");
        return KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(modid, id));
    }
}
