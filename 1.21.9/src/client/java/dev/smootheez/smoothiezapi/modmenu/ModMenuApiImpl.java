package dev.smootheez.smoothiezapi.modmenu;

import com.terraformersmc.modmenu.api.*;
import dev.smootheez.smoothiezapi.gui.screen.*;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }
}
