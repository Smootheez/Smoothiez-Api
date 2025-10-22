package io.github.smootheez.smoothiezapi.config.option;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.config.serializer.*;
import io.github.smootheez.smoothiezapi.gui.widget.entries.handler.*;
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
