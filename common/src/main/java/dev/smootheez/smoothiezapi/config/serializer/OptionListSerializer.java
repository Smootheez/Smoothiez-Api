package dev.smootheez.smoothiezapi.config.serializer;

import com.google.gson.*;
import dev.smootheez.smoothiezapi.config.*;

import java.util.*;

public class OptionListSerializer implements ConfigOptionSerializer<OptionList> {
    @Override
    public JsonElement serialize(OptionList value) {
        JsonArray jsonArray = new JsonArray();
        for (String item : value.values()) {
            jsonArray.add(item);
        }
        return jsonArray;
    }

    @Override
    public OptionList deserialize(JsonElement json) {
        JsonArray jsonArray = json.getAsJsonArray();
        List<String> values = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            values.add(element.getAsString());
        }
        return new OptionList(values);
    }
}
