package io.github.smootheez.smoothiezapi.gui.widget.base;

import net.fabricmc.api.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public abstract class ConfigWidgetEntry extends ContainerObjectSelectionList.Entry<ConfigWidgetEntry> {
    @Nullable
    public final List<FormattedCharSequence> tooltip;

    protected ConfigWidgetEntry(@Nullable List<FormattedCharSequence> tooltip) {
        this.tooltip = tooltip;
    }
}
