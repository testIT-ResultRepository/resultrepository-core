package info.novatec.testit.resultrepository.remote.v1;

import java.util.List;
import java.util.Set;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link TestResult test results}.
 *
 * @since 2.0.0
 */
public interface TestResultsRemoteService {

    /**
     * Looks up a {@link TestResultData test result} by it's unique ID.
     *
     * @param testResultId the unique ID of the test result
     * @return the found test result
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestResultData findById(Long testResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link TestData test} of a {@link TestResultData test
     * result}. The test result is identified by it's unique ID. If there is no
     * link to a test, <code>null</code> is returned.
     *
     * @param testResultId the unique ID of the test result
     * @return the found test or <code>null</code>
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestData findTest(Long testResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link TestGroupResultData test group result} of a
     * {@link TestResultData test result}. The test result is identified by it's
     * unique ID. If there is no link to a test group result, <code>null</code>
     * is returned.
     *
     * @param testResultId the unique ID of the test result
     * @return the found test group result or <code>null</code>
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestGroupResultData findTestGroupResult(Long testResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link TagData tags} of a {@link TestResultData test result}
     * . The test result is identified by it's unique ID.
     *
     * @param testResultId the unique ID of the test result
     * @return all tags of the test result
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TagData> findTags(Long testResultId) throws RemoteOperationException;

    /**
     * Looks up the {@link TestResultDetailData details} of a
     * {@link TestResultData test result} . The test result is identified by
     * it's unique ID. The details are returned an chronologically sorted list.
     * THe first element of the list is the oldest detail.
     *
     * @param testResultId the unique ID of the test result
     * @return all details of the test result
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    List<TestResultDetailData> findTestResultDetails(Long testResultId) throws RemoteOperationException;

    /**
     * Calculates a {@link TestResultStatistic statistic} of a
     * {@link TestResultData test result}. The test result is identified by it's
     * unique ID.
     *
     * @param testResultId the unique ID of the test result
     * @return the calculated statistic
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestResultStatistic calculateStatistic(Long testResultId) throws RemoteOperationException;

}
