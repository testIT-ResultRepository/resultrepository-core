package info.novatec.testit.resultrepository.metrics.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.resultrepository.metrics.aspects.MeasuredAspect;


/**
 * This annotation marks the execution of methods of spring beans to be measured
 * using a {@linkplain MeasuredAspect measured aspect}. This will only work if the
 * class containing annotated methods is initialized via the spring context!
 *
 * @see MeasuredAspect
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Measured {
    // tagging interface to trigger behavior
}
