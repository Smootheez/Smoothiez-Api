package io.github.smootheez.smoothiezapi.gui.widget.entries;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.widget.base.*;
import net.fabricmc.api.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class TextDoubleWidgetEntry extends TextWidgetEntry<Double> {
    public TextDoubleWidgetEntry(String label, @Nullable List<FormattedCharSequence> description, ConfigOption<Double> option) {
        super(label, description, option);
    }

    @Override
    protected void textFieldResponder(String s) {
        if (validateDouble(s) && Double.parseDouble(s) >= this.option.getMinValue() && Double.parseDouble(s) <= this.option.getMaxValue()) {
            setOptionValue(Double.valueOf(s));
            this.textField.setTextColor(-2039584);
        } else {
            this.textField.setTextColor(-65536);
        }
    }

    private boolean validateDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
