package dev.smootheez.smoothiezapi.config.serializer;

import com.google.gson.*;
import dev.smootheez.smoothiezapi.config.*;

public class EnumSerializer<E extends Enum<E>> implements ConfigOptionSerializer<E> {
    private final Class<E> enumClass;

    public EnumSerializer(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public JsonElement serialize(E value) {
        return new JsonPrimitive(value.name());
    }

    @Override
    public E deserialize(JsonElement json) {
        return Enum.valueOf(enumClass, json.getAsString());
    }
}
