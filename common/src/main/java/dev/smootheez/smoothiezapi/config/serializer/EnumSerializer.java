package dev.smootheez.smoothiezapi.config.serializer;

import com.google.gson.*;
import dev.smootheez.smoothiezapi.config.*;

public record EnumSerializer<E extends Enum<E>>(Class<E> enumClass) implements ConfigOptionSerializer<E> {
    @Override
    public JsonElement serialize(E value) {
        return new JsonPrimitive(value.name());
    }

    @Override
    public E deserialize(JsonElement json) {
        return Enum.valueOf(enumClass, json.getAsString());
    }
}
