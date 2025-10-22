package io.github.smootheez.smoothiezapi.config.serializer;

import com.google.gson.*;
import io.github.smootheez.smoothiezapi.config.*;

public class DoubleSerializer implements ConfigOptionSerializer<Double> {
    @Override
    public JsonElement serialize(Double value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Double deserialize(JsonElement json) {
        return json.getAsDouble();
    }
}
