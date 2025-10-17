package dev.smootheez.smoothiezapi.gui.widget.entries.handler;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.gui.widget.base.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.*;
import net.fabricmc.api.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface WidgetHandler<T> {
    String CONFIG_WIDGET = "config.widget.";

    ConfigWidgetEntry createWidget(ConfigOption<T> option, String configId, @Nullable List<FormattedCharSequence> tooltip);

    @Environment(EnvType.CLIENT)
    class BooleanHandler implements WidgetHandler<Boolean> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<Boolean> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new BooleanWidgetEntry(Component.translatable(CONFIG_WIDGET + configId + "." + option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class IntegerHandler implements WidgetHandler<Integer> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<Integer> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new TextIntegerWidgetEntry(Component.translatable(CONFIG_WIDGET + configId + "." + option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class DoubleHandler implements WidgetHandler<Double> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<Double> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new TextDoubleWidgetEntry(Component.translatable(CONFIG_WIDGET + configId + "." + option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class StringHandler implements WidgetHandler<String> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<String> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new TextStringWidgetEntry(Component.translatable(CONFIG_WIDGET + configId + "." + option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class OptionListHandler implements WidgetHandler<OptionList> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<OptionList> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new OptionListWidgetEntry(Component.translatable(CONFIG_WIDGET + configId + "." + option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class EnumHandler<E extends Enum<E>> implements WidgetHandler<E> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<E> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new CycleWidgetEntry<>(Component.translatable(CONFIG_WIDGET + configId + "." + option.getKey()), tooltip, option);
        }
    }
}
