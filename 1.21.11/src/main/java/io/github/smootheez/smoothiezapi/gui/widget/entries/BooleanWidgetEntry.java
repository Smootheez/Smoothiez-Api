package io.github.smootheez.smoothiezapi.gui.widget.entries;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.widget.base.*;
import net.fabricmc.api.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class BooleanWidgetEntry extends LabeledWidgetEntry<Boolean> {
    private final CycleButton<Boolean> toggleButton;

    public BooleanWidgetEntry(String label, @Nullable List<FormattedCharSequence> description, ConfigOption<Boolean> option) {
        super(label, description, option);
        this.toggleButton = CycleButton.onOffBuilder(this.option.getValue())
                .displayOnlyValue()
                .create(0, 0, 80, 20, Component.empty(),
                        (cycleButton, value) -> setOptionValue(value));

        addRightAlignedWidget(this.toggleButton);
    }

    @Override
    protected void updateWidgetState() {
        this.toggleButton.setValue(this.option.getValue());
    }
}
