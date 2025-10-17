package dev.smootheez.smoothiezapi.config;

import java.util.*;

public record OptionList(List<String> values) {
    public OptionList(List<String> values) {
        this.values = new ArrayList<>(values);
    }

    public void add(String value) {
        this.values.add(value);
    }

    public void edit(String oldValue, String newValue) {
        int index = this.values.indexOf(oldValue);
        if (index != -1) {
            this.values.set(index, newValue);
        }
    }

    public void remove(String value) {
        this.values.remove(value);
    }

    public void clear() {
        this.values.clear();
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
