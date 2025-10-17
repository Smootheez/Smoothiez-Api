package dev.smootheez.smoothiezapi.modmenu;

import com.terraformersmc.modmenu.api.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.example.*;
import dev.smootheez.smoothiezapi.gui.screen.*;
import net.fabricmc.api.*;

@Environment(EnvType.CLIENT)
public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> new ConfigScreen(screen, ConfigManager.getConfig(ExampleConfig.class));
    }
}
