package dev.smootheez.smoothiezapi.config;

import java.util.*;

public record OptionList(List<String> values) {

    public OptionList(List<String> values) {
        this.values = new ArrayList<>(values);
    }
}
