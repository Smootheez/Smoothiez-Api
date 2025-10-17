package dev.smootheez.smoothiezapi.gui.widget.entries;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.screen.*;
import dev.smootheez.smoothiezapi.gui.widget.base.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class OptionListWidgetEntry extends LabeledWidgetEntry<OptionList> {
    private final Button button;

    public OptionListWidgetEntry(Component label, @Nullable List<FormattedCharSequence> description, ConfigOption<OptionList> option) {
        super(label, description, option);
        this.button = Button.builder(Component.literal("Edit List"),
                btn -> this.openOptionListScreen()).size(80, 20).build();

        addRightAlignedWidget(this.button);
    }

    public ConfigOption<OptionList> getOption() {
        return this.option;
    }

    private void openOptionListScreen() {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new OptionListScreen(mc.screen, this));
    }
}
