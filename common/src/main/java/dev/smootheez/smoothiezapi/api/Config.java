package dev.smootheez.smoothiezapi.api;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {
    String name();

    boolean autoGui() default false;
}
