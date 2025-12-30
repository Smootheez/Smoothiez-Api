package io.github.smootheez.smoothiezapi.config.option;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.config.serializer.*;
import io.github.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class EnumOption<E extends Enum<E>> extends ConfigOption<E> {
    private final Class<E> enumClass;

    public EnumOption(String key, E defaultValue) {
        super(key, defaultValue, null, null);
        this.enumClass = defaultValue.getDeclaringClass();
    }

    @Override
    public @NotNull Class<E> getType() {
        return enumClass;
    }

    @Override
    public @NotNull WidgetHandler<E> getWidgetHandler() {
        return new WidgetHandler.EnumHandler<>();
    }

    @Override
    public @NotNull ConfigOptionSerializer<E> getSerializer() {
        return new EnumSerializer<>(enumClass);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EnumOption<?> that = (EnumOption<?>) o;
        return Objects.equals(enumClass, that.enumClass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enumClass);
    }
}
