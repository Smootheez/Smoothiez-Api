package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.config.serializer.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import org.jetbrains.annotations.*;

public class BooleanOption extends ConfigOption<Boolean> {
    public BooleanOption(String key, Boolean defaultValue) {
        super(key, defaultValue, null, null);
    }

    @Override
    public @NotNull Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public @NotNull WidgetHandler<Boolean> getWidgetHandler() {
        return new WidgetHandler.BooleanHandler();
    }

    @Override
    public @NotNull ConfigOptionSerializer<Boolean> getSerializer() {
        return new BooleanSerializer();
    }
}
