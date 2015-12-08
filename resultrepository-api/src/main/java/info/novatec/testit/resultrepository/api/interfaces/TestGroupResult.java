package info.novatec.testit.resultrepository.api.interfaces;

import java.util.Set;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithCustomProperties;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithMetadataValues;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTags;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a single {@link TestGroup test group's} execution result. Every
 * time a test group is executed a new instance of this type will be stored
 * within the ResultRepository.
 * <p>
 * It stores references to it's {@link TestGroup test group}, {@link Build
 * build} and all of its {@link TestResult test results}.
 *
 * @see #getTestGroup()
 * @see #getBuild()
 * @see #getTestResults()
 * @since 2.0.0
 */
public interface TestGroupResult
    extends EntityWithID, EntityWithTags, EntityWithMetadataValues, EntityWithTimestamp, EntityWithCustomProperties {

    /**
     * Returns all of this {@linkplain TestGroupResult test group result's}
     * {@linkplain TestResult test results}.
     *
     * @return the test results
     * @since 2.0.0
     */
    Set<? extends TestResult> getTestResults();

    /**
     * Returns this {@linkplain TestGroupResult test group result's}
     * {@link TestGroup test group}. This returns null in case the test group
     * result is not linked to any test group.
     *
     * @return the test group - may be null
     * @since 2.0.0
     */
    TestGroup getTestGroup();

    /**
     * Returns this {@linkplain TestGroupResult test group result's}
     * {@link Build build}. This returns null in case the test group result is
     * not linked to any build.
     *
     * @return the build - may be null
     * @since 2.0.0
     */
    Build getBuild();

}
