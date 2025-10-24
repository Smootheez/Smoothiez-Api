package io.github.smootheez.smoothiezapi.gui.widget.entries.handler;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.gui.widget.base.*;
import io.github.smootheez.smoothiezapi.gui.widget.entries.*;
import net.fabricmc.api.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static io.github.smootheez.smoothiezapi.gui.helper.ConfigTranslationHelper.*;

@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface WidgetHandler<T> {
    ConfigWidgetEntry createWidget(ConfigOption<T> option, String configId, @Nullable List<FormattedCharSequence> tooltip);

    @Environment(EnvType.CLIENT)
    class BooleanHandler implements WidgetHandler<Boolean> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<Boolean> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new BooleanWidgetEntry(getTranslationKey(configId, option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class TextIntegerHandler implements WidgetHandler<Integer> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<Integer> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new TextIntegerWidgetEntry(getTranslationKey(configId, option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class TextDoubleHandler implements WidgetHandler<Double> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<Double> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new TextDoubleWidgetEntry(getTranslationKey(configId, option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class StringHandler implements WidgetHandler<String> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<String> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new TextStringWidgetEntry(getTranslationKey(configId, option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class OptionListHandler implements WidgetHandler<OptionList> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<OptionList> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new OptionListWidgetEntry(getTranslationKey(configId, option.getKey()), tooltip, option);
        }
    }

    @Environment(EnvType.CLIENT)
    class EnumHandler<E extends Enum<E>> implements WidgetHandler<E> {
        @Override
        public ConfigWidgetEntry createWidget(ConfigOption<E> option, String configId, @Nullable List<FormattedCharSequence> tooltip) {
            return new CycleWidgetEntry<>(getTranslationKey(configId, option.getKey()), tooltip, option);
        }
    }
}
