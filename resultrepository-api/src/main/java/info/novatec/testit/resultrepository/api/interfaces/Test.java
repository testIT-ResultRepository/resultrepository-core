package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a single test. I.e. this could be a single method contained within
 * a JUnit test class.
 *
 * @since 2.0.0
 */
public interface Test extends EntityWithID, EntityWithTimestamp {

    /**
     * Returns this {@link Test test's} unique name. This is a required property
     * and may never return null!
     *
     * @return the name of the test
     * @since 2.0.0
     */
    String getName();

}
