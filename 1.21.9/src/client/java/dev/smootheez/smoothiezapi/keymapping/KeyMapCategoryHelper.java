package dev.smootheez.smoothiezapi.keymapping;

import net.minecraft.client.*;
import net.minecraft.resources.*;

import java.util.*;

public interface KeyMapCategoryHelper {
    static KeyMapping.Category register(String modid, String id) {
        Objects.requireNonNull(modid, "Mod id cannot be null!");
        Objects.requireNonNull(id, "Id cannot be null!");
        return KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(modid, id));
    }
}
