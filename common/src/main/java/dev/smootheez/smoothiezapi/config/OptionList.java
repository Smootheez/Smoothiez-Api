package dev.smootheez.smoothiezapi.config;

import java.util.*;

public record OptionList(List<String> values) {
    public OptionList(List<String> values) {
        this.values = new ArrayList<>(values);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OptionList that = (OptionList) o;
        return Objects.equals(values(), that.values());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values());
    }
}
