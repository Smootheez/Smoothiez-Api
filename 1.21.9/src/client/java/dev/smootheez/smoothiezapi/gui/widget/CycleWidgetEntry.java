package dev.smootheez.smoothiezapi.gui.widget;

import dev.smootheez.smoothiezapi.config.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CycleWidgetEntry<T extends Enum<T>> extends LabeledWidgetEntry<T> {
    private final CycleButton<T> cycleButton;

    protected CycleWidgetEntry(Component label, @Nullable List<FormattedCharSequence> description, ConfigOption<T> option) {
        super(label, description, option);
        T[] enumValues = this.option.getType().getEnumConstants();

        this.cycleButton = CycleButton.<T>builder(e -> Component.translatable(toCammelCase(e.name())))
                .displayOnlyValue()
                .withValues(enumValues)
                .withInitialValue(this.option.getValue())
                .create(0, 0, 80, 20, Component.literal(""),
                        (cycleButton1, value) -> {
                            this.option.setValue(value);
                            updateResetButtonState();
                        });

        addRightAlignedWidget(cycleButton);
    }

    @Override
    protected void updateWidgetState() {
        cycleButton.setValue(this.option.getValue());
    }

    private String toCammelCase(String name) {
        StringBuilder result = new StringBuilder();
        String[] parts = name.toLowerCase().split("_");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty())
                continue;
            if (i == 0) {
                result.append(part);
            } else {
                result.append(part.substring(0, 1).toUpperCase());
                result.append(part.substring(1));
            }
        }
        return result.toString();
    }
}
