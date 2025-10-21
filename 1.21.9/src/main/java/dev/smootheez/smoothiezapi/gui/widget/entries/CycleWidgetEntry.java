package dev.smootheez.smoothiezapi.gui.widget.entries;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.widget.base.*;
import net.fabricmc.api.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class CycleWidgetEntry<T extends Enum<T>> extends LabeledWidgetEntry<T> {
    private final CycleButton<T> cycleButton;

    public CycleWidgetEntry(Component label, @Nullable List<FormattedCharSequence> description, ConfigOption<T> option) {
        super(label, description, option);
        T[] enumValues = this.option.getType().getEnumConstants();

        this.cycleButton = CycleButton.<T>builder(e ->
                        Component.translatable(label.getString() + "." + e.name().toLowerCase(Locale.ROOT)))
                .displayOnlyValue()
                .withValues(enumValues)
                .withInitialValue(this.option.getValue())
                .create(0, 0, 80, 20, Component.literal(""),
                        (cycleButton1, value) -> setOptionValue(value));

        addRightAlignedWidget(cycleButton);
    }

    @Override
    protected void updateWidgetState() {
        cycleButton.setValue(this.option.getValue());
    }
}
