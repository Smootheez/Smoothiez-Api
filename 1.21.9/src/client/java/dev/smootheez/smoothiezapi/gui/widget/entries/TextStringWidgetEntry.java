package dev.smootheez.smoothiezapi.gui.widget.entries;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.widget.base.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class TextStringWidgetEntry extends TextWidgetEntry<String> {
    public TextStringWidgetEntry(Component label, @Nullable List<FormattedCharSequence> description, ConfigOption<String> option) {
        super(label, description, option);
    }

    @Override
    protected void textFieldResponder(String s) {
        this.option.setValue(s);
        this.textField.setTextColor(-2039584);
        super.textFieldResponder(s);
    }
}
