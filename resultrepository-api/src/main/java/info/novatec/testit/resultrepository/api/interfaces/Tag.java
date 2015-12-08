package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a tag within the ResultRepository. Tags are unique words and / or
 * sentences which can be linked to {@link Build builds}, {@link TestGroupResult
 * test group results} and {@link TestResult test results}.
 *
 * @since 2.0.0
 */
public interface Tag extends EntityWithID, EntityWithTimestamp {

    /**
     * Returns this {@link Tag tag's} unique value. This is a required property
     * and may never return null!
     *
     * @return the value of the tag
     * @since 2.0.0
     */
    String getValue();

}
