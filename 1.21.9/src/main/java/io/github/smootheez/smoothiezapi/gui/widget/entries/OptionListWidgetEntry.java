package io.github.smootheez.smoothiezapi.gui.widget.entries;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.screen.*;
import io.github.smootheez.smoothiezapi.gui.widget.base.*;
import io.github.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class OptionListWidgetEntry extends LabeledWidgetEntry<OptionList> {
    public OptionListWidgetEntry(Component label, @Nullable List<FormattedCharSequence> description, ConfigOption<OptionList> option) {
        super(label, description, option);

        addRightAlignedWidget(Button.builder(Component.translatable("widget." + Constants.MOD_ID + ".configure.option_list"),
                btn -> this.openOptionListScreen()).size(80, 20).build());
    }

    public ConfigOption<OptionList> getOption() {
        return this.option;
    }

    private void openOptionListScreen() {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new OptionListScreen(mc.screen, this));
    }
}
