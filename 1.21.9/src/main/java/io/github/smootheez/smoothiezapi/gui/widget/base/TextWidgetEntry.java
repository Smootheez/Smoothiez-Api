package io.github.smootheez.smoothiezapi.gui.widget.base;

import io.github.smootheez.smoothiezapi.config.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public abstract class TextWidgetEntry<T> extends LabeledWidgetEntry<T>  {
    protected final EditBox textField;

    protected TextWidgetEntry(Component label, @Nullable List<FormattedCharSequence> description, ConfigOption<T> option) {
        super(label, description, option);
        this.textField = new EditBox(Minecraft.getInstance().font, 0, 0, 80, 20, Component.literal(""));
        this.textField.setValue(this.option.getValue().toString());
        this.textField.setResponder(this::textFieldResponder);

        addRightAlignedWidget(this.textField);
    }

    @Override
    protected void updateWidgetState() {
        this.textField.setValue(this.option.getDefaultValue().toString());
    }

    protected abstract void textFieldResponder(String s);
}
