package io.infectnet.server.engine.core.util.hook;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Qualifier
public @interface PostSetupHook {
}
