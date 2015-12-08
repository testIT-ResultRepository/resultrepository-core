package info.novatec.testit.resultrepository.api.interfaces;

import java.util.List;

import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithCustomProperties;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithMetadataValues;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTags;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a single {@link Test test's} execution result. Every time a test
 * is executed a new instance of this type will be stored within the
 * ResultRepository.
 * <p>
 * It stores references to it's {@link Test test} and all of its
 * {@link TestResultDetail test result details}. As well as the
 * {@link #getDuration() duration} and {@link #getStatus() result status} of the
 * execution.
 *
 * @see #getTest()
 * @see #getTestResultDetails()
 * @since 2.0.0
 */
public interface TestResult
    extends EntityWithID, EntityWithTags, EntityWithMetadataValues, EntityWithTimestamp, EntityWithCustomProperties {

    /**
     * Returns this {@linkplain TestResult test result's} duration. The duration
     * is the amount of time the test took to complete in milliseconds. If there
     * is no duration set 0 is returned.
     *
     * @return the duration
     * @since 2.0.0
     */
    Long getDuration();

    /**
     * Returns all of this {@linkplain TestResult test result's}
     * {@linkplain TestResultDetail details} as a {@linkplain List list}. This
     * list is ordered ascending by the detail's creation timestamp (oldest
     * first).
     *
     * @return the test result details
     * @since 2.0.0
     */
    List<? extends TestResultDetail> getTestResultDetails();

    /**
     * Returns this {@linkplain TestResult test result's} {@link Test test}.
     * This returns null in case the result is not linked to any test.
     *
     * @return the test - may be null
     * @since 2.0.0
     */
    Test getTest();

    /**
     * Returns this {@linkplain TestResult test result's}
     * {@linkplain ResultStatus status}. If no status information is available
     * it will default to {@linkplain ResultStatus#UNKNOWN}.
     *
     * @return the status
     * @since 2.0.0
     */
    ResultStatus getStatus();

}
