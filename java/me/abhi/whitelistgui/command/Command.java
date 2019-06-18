package me.abhi.whitelistgui.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name();

    String[] aliases() default "";

    String permission() default "";

    boolean playersOnly() default false;
}
