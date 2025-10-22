package dev.smootheez.example.config;

import dev.smootheez.example.util.*;
import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.config.option.*;

@Config(name = Constants.MOD_ID, autoGui = true)
public class ExampleConfig implements ConfigApi {
    private final BooleanOption booleanExample = new BooleanOption("exampleBoolean", true);
    private final IntegerOption integerExample = new IntegerOption("exampleInteger", 10, 0, 20);
    private final DoubleOption doubleExample = new DoubleOption("exampleDouble", 10.5, 0.0, 20.0);
    private final StringOption stringExample = new StringOption("exampleString", "Hello World");
    private final ListOption optionListExample = new ListOption("exampleOptionList", "Option1", "Option2", "Option3");
    private final EnumOption<ExampleEnum> enumExample = new EnumOption<>("exampleEnum", ExampleEnum.OPTION_A);

    public EnumOption<ExampleEnum> getEnumExample() {
        return enumExample;
    }

    public ListOption getOptionListExample() {
        return optionListExample;
    }

    public StringOption getStringExample() {
        return stringExample;
    }

    public DoubleOption getDoubleExample() {
        return doubleExample;
    }

    public IntegerOption getIntegerExample() {
        return integerExample;
    }

    public BooleanOption getBooleanExample() {
        return booleanExample;
    }

    enum AnotherEnum {}

    public enum ExampleEnum {
        OPTION_A,
        OPTION_B,
        OPTION_C
    }
}
