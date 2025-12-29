package io.github.smootheez.smoothiezapi.gui.widget.entries;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.widget.base.*;
import net.fabricmc.api.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class TextIntegerWidgetEntry extends TextWidgetEntry<Integer> {
    public TextIntegerWidgetEntry(String label, @Nullable List<FormattedCharSequence> description, ConfigOption<Integer> option) {
        super(label, description, option);
    }

    @Override
    protected void textFieldResponder(String s) {
        if (validateInteger(s) && Integer.parseInt(s) >= this.option.getMinValue() && Integer.parseInt(s) <= this.option.getMaxValue()) {
            setOptionValue(Integer.valueOf(s));
            this.textField.setTextColor(-2039584);
        } else {
            this.textField.setTextColor(-65536);
        }
    }

    private boolean validateInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
