package io.infectnet.server.engine.content.player;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 * Qualifier annotation for injecting the Environment player.
 */
@Target({METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
@Qualifier
public @interface Environment {
}
