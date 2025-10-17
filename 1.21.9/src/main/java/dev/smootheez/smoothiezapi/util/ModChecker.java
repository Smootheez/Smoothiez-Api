package dev.smootheez.smoothiezapi.util;

import net.fabricmc.loader.api.*;

public class ModChecker {
    private ModChecker() {}

    public static boolean isModInstalled(String modId) {
        if (modId == null || modId.isEmpty()) return false;
        for (ModContainer mod : FabricLoader.getInstance().getAllMods())
            if (mod.getMetadata().getId().equals(modId)) return true;
        return false;
    }
}
