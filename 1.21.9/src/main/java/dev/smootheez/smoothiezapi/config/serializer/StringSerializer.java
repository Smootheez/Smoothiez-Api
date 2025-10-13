package dev.smootheez.smoothiezapi.config.serializer;

import com.google.gson.*;
import dev.smootheez.smoothiezapi.config.*;

public class StringSerializer implements ConfigOptionSerializer<String> {
    @Override
    public JsonElement serialize(String value) {
        return new JsonPrimitive(value);
    }

    @Override
    public String deserialize(JsonElement json) {
        return json.getAsString();
    }
}
