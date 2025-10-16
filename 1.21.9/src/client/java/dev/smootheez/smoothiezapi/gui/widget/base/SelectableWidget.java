package dev.smootheez.smoothiezapi.gui.widget.base;

import dev.smootheez.smoothiezapi.config.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.apache.commons.compress.utils.*;

import java.util.*;

public abstract class SelectableWidget<T> {
    private final Font font = Minecraft.getInstance().font;
    private final List<FormattedCharSequence> label;
    protected final List<AbstractWidget> children = Lists.newArrayList();
    private final ConfigOption<T> option;

    protected SelectableWidget(Component label, ConfigOption<T> option) {
        this.label = this.font.split(label, 200);
        this.option = option;
    }
}
