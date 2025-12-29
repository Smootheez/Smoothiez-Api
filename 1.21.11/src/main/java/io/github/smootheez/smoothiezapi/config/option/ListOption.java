package io.github.smootheez.smoothiezapi.config.option;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.config.serializer.*;
import io.github.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ListOption extends ConfigOption<OptionList> {
    public ListOption(String key, String... defaultValue) {
        super(key, new OptionList(Arrays.asList(defaultValue)), null, null);
    }

    @Override
    public @NotNull Class<OptionList> getType() {
        return OptionList.class;
    }

    @Override
    public @NotNull WidgetHandler<OptionList> getWidgetHandler() {
        return new WidgetHandler.OptionListHandler();
    }

    @Override
    public @NotNull ConfigOptionSerializer<OptionList> getSerializer() {
        return new OptionListSerializer();
    }
}
