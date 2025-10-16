package dev.smootheez.smoothiezapi.gui.widget.base;

import net.minecraft.client.gui.components.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class ConfigWidgetEntry extends ContainerObjectSelectionList.Entry<ConfigWidgetEntry> {
    @Nullable
    public final List<FormattedCharSequence> tooltip;

    protected ConfigWidgetEntry(@Nullable List<FormattedCharSequence> tooltip) {
        this.tooltip = tooltip;
    }
}
