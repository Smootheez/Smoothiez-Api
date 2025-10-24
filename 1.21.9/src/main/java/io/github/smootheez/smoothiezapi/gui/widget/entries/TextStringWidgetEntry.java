package io.github.smootheez.smoothiezapi.gui.widget.entries;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.widget.base.*;
import net.fabricmc.api.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class TextStringWidgetEntry extends TextWidgetEntry<String> {
    public TextStringWidgetEntry(String label, @Nullable List<FormattedCharSequence> description, ConfigOption<String> option) {
        super(label, description, option);
    }

    @Override
    protected void textFieldResponder(String s) {
        setOptionValue(s);
        this.textField.setTextColor(-2039584);
    }
}
