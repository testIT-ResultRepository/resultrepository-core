package info.novatec.testit.resultrepository.persistence.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.resultrepository.persistence.aspects.TransactedAspect;


/**
 * This annotation marks methods of spring beans to be executed within a
 * transactional context using a {@link TransactedAspect transacted aspect}.
 * This will only work if the class containing annotated methods is initialized
 * via the spring context!
 *
 * @see TransactedAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface Transacted {
    // no special properties, yet
}
