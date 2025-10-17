package dev.smootheez.smoothiezapi.modmenu;

import com.terraformersmc.modmenu.api.*;
import dev.smootheez.smoothiezapi.util.*;

import java.util.*;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return ModMenuUtil.CONFIG_SCREEN_FACTORY;
    }
}
