package info.novatec.testit.resultrepository.remote.v1;

import java.util.Set;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link TestGroupResult test group results}.
 *
 * @since 2.0.0
 */
public interface TestGroupResultsRemoteService {

    /**
     * Looks up a {@link TestGroupResultData test group result} by it's unique
     * ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return the found test group result
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestGroupResultData findById(Long testGroupResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link TestGroupData test group} of a
     * {@link TestGroupResultData test group result}. The test group result is
     * identified by it's unique ID. If there is no link to a test group,
     * <code>null</code> is returned.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return the found test group or <code>null</code>
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestGroupData findTestGroup(Long testGroupResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link BuildData build} of a {@link TestGroupResultData test
     * group result}. The test group result is identified by it's unique ID. If
     * there is no link to a build, <code>null</code> is returned.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return the found build or <code>null</code>
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    BuildData findBuild(Long testGroupResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link TagData tags} of a {@link TestGroupResultData test
     * group result} . The test group result is identified by it's unique ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return all tags of the test group result
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TagData> findTags(Long testGroupResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link TestResultData test results} of a
     * {@link TestGroupResultData test group result} . The test group result is
     * identified by it's unique ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return all test results of the test group result
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestResultData> findTestResults(Long testGroupResultId) throws RemoteOperationException;

    /**
     * Calculates a {@link TestGroupResultStatistic statistic} of a
     * {@link TestGroupResultData test group result}. The test group result is
     * identified by it's unique ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return the calculated statistic
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestGroupResultStatistic calculateStatistic(Long testGroupResultId) throws RemoteOperationException;

}
