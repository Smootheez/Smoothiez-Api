package io.github.smootheez.smoothiezapi.config.serializer;

import com.google.gson.*;
import io.github.smootheez.smoothiezapi.config.*;

public class IntegerSerializer implements ConfigOptionSerializer<Integer> {
    @Override
    public JsonElement serialize(Integer value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Integer deserialize(JsonElement json) {
        return json.getAsInt();
    }
}
