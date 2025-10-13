package dev.smootheez.smoothiezapi.config.serializer;

import com.google.gson.*;
import dev.smootheez.smoothiezapi.config.*;

public class BooleanSerializer implements ConfigOptionSerializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Boolean deserialize(JsonElement json) {
        return json.getAsBoolean();
    }
}
