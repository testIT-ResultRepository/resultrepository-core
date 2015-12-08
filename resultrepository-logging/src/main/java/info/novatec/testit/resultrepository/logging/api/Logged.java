package info.novatec.testit.resultrepository.logging.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.resultrepository.logging.aspects.LoggedAspect;


/**
 * This annotation marks the execution of methods of spring beans to be logged
 * using a {@linkplain LoggedAspect logged execution aspect}. This will only work if
 * the class containing annotated methods is initialized via the spring context!
 *
 * @see LoggedAspect
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logged {

    LogLevel logLevel() default LogLevel.DEBUG;

    boolean printArguments() default false;

}
