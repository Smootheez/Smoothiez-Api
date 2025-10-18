package dev.smootheez.example.config;

import dev.smootheez.example.util.*;
import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.config.*;

@Config(name = Constants.MOD_ID, autoGui = true)
public class ExampleConfig implements ConfigApi {
    private final ConfigOption<Boolean> booleanExample = ConfigOption.create("exampleBoolean", true);
    private final ConfigOption<Integer> integerExample = ConfigOption.create("exampleInteger", 10, 0, 20);
    private final ConfigOption<Double> doubleExample = ConfigOption.create("exampleDouble", 10.5, 0.0, 20.0);
    private final ConfigOption<String> stringExample = ConfigOption.create("exampleString", "Hello World");
    private final ConfigOption<OptionList> optionListExample = ConfigOption.create("exampleOptionList", "Option1", "Option2", "Option3");
    private final ConfigOption<ExampleEnum> enumExample = ConfigOption.create("exampleEnum", ExampleEnum.OPTION_A);

    public ConfigOption<Boolean> getBooleanExample() {
        return booleanExample;
    }

    public ConfigOption<Integer> getIntegerExample() {
        return integerExample;
    }

    public ConfigOption<Double> getDoubleExample() {
        return doubleExample;
    }

    public ConfigOption<String> getStringExample() {
        return stringExample;
    }

    public ConfigOption<OptionList> getOptionListExample() {
        return optionListExample;
    }

    public ConfigOption<ExampleEnum> getEnumExample() {
        return enumExample;
    }

    public enum ExampleEnum {
        OPTION_A,
        OPTION_B,
        OPTION_C
    }
}
