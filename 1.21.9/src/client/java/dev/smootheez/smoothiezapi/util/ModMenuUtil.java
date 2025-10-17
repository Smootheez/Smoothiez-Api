package dev.smootheez.smoothiezapi.util;

import com.terraformersmc.modmenu.api.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.screen.*;

import java.util.*;

public class ModMenuUtil {
    public static final Map<String, ConfigScreenFactory<?>> CONFIG_SCREEN_FACTORY = new HashMap<>();
    private ModMenuUtil() {}

    public static void registerConfigScreenFactory(String modId, List<ConfigOption<?>> options) {
        if (ModChecker.isModInstalled(modId) && ModChecker.isModInstalled("modmenu"))
            CONFIG_SCREEN_FACTORY.putIfAbsent(modId, parent -> new ConfigScreen(parent, modId, options));
    }
}
