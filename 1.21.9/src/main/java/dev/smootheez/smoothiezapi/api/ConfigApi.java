package dev.smootheez.smoothiezapi.api;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.util.*;

import java.lang.reflect.*;
import java.util.*;

public interface ConfigApi {
    default List<BaseConfigOption> getAllOptions() {
        List<BaseConfigOption> options = new ArrayList<>();
        Class<?> clazz = this.getClass();
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.getParameterCount() == 0)
                .filter(method -> BaseConfigOption.class.isAssignableFrom(method.getReturnType()))
                .forEach(method -> {
                    try {
                        Object value = method.invoke(this);
                        if (value instanceof BaseConfigOption opt) {
                            options.add(opt);
                        }
                    } catch (ReflectiveOperationException e) {
                        Constants.LOGGER.error(
                                "Failed to get config option from method: {}, error: {}",
                                method.getName(), e.getMessage()
                        );
                    }
                });
        return options;
    }
}

