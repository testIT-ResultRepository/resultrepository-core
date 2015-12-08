package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a single information detail of a {@link TestResult test's
 * execution result}. These details are optional and depending on who is
 * reporting the results test results might not contain any of them. The are
 * mainly intended to allow for the storage of additional information in order
 * to optimize tractability of test results. Possible details could be a stack
 * trace or the result of multiple checkpoints.
 *
 * @since 2.0.0
 */
public interface TestResultDetail extends EntityWithID, EntityWithTimestamp {

    /**
     * Returns this {@linkplain TestResultDetail test result detail's}
     * {@linkplain DetailType type}. If no type information is available it will
     * default to {@linkplain DetailType#INFORMATION}.
     *
     * @return the type
     * @since 2.0.0
     */
    DetailType getType();

    /**
     * Returns this {@linkplain TestResultDetail test result detail's} message.
     * May return null if no message is available.
     *
     * @return the message
     * @since 2.0.0
     */
    String getMessage();

}
