package dev.smootheez.smoothiezapi.gui.widget;

import net.minecraft.client.gui.components.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class ConfigWidgetEntry extends ContainerObjectSelectionList.Entry<ConfigWidgetEntry> {
    @Nullable
    private final List<FormattedCharSequence> description;

    protected ConfigWidgetEntry(@Nullable List<FormattedCharSequence> description) {
        this.description = description;
    }
}
