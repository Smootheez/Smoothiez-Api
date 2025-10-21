package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.config.serializer.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import org.jetbrains.annotations.*;

public class DoubleOption extends ConfigOption<Double> {
    public DoubleOption(String key, Double defaultValue, Double minValue, Double maxValue) {
        super(key, defaultValue, minValue, maxValue);
        if (minValue > maxValue || defaultValue < minValue || defaultValue > maxValue) trhowIllegalArgumentException();
    }

    @Override
    public @NotNull Class<Double> getType() {
        return Double.class;
    }

    @Override
    public @NotNull WidgetHandler<Double> getWidgetHandler() {
        return new WidgetHandler.DoubleHandler();
    }

    @Override
    public @NotNull ConfigOptionSerializer<Double> getSerializer() {
        return new DoubleSerializer();
    }
}
