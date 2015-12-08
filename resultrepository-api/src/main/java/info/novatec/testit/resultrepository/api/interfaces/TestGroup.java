package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a group of {@link Test tests}. I.e. this could be a test class in
 * JUnit.
 *
 * @since 2.0.0
 */
public interface TestGroup extends EntityWithID, EntityWithTimestamp {

    /**
     * Returns the {@link TestGroup test group's} unique name. This is a
     * required property and may never return null!
     *
     * @return the name of the test group
     * @since 2.0.0
     */
    String getName();

}
