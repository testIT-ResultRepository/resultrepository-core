package info.novatec.testit.resultrepository.server.api;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding
 * {@link TestResult test results}.
 */
public interface TestResultsService {

    /**
     * Looks up a {@link TestResultData test result} by it's unique ID.
     *
     * @param testResultId the unique ID of the test result
     * @return the found test result
     * @throws DataNotFoundException if no test result for the given ID was
     * found
     */
    TestResultData findById(Long testResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TestData test} of a {@link TestResultData test
     * result}. The test result is identified by it's unique ID. If there is no
     * link to a test, <code>null</code> is returned.
     *
     * @param testResultId the unique ID of the test result
     * @return the found test or <code>null</code>
     * @throws DataNotFoundException if no test result for the given ID was
     * found
     */
    TestData findTest(Long testResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TestGroupResultData test group result} of a
     * {@link TestResultData test result}. The test result is identified by it's
     * unique ID. If there is no link to a test group result, <code>null</code>
     * is returned.
     *
     * @param testResultId the unique ID of the test result
     * @return the found test group result or <code>null</code>
     * @throws DataNotFoundException if no test result for the given ID was
     * found
     */
    TestGroupResultData findTestGroupResult(Long testResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TagData tags} of a {@link TestResultData test result}
     * . The test result is identified by it's unique ID.
     *
     * @param testResultId the unique ID of the test result
     * @return all tags of the test result
     * @throws DataNotFoundException if no test result for the given ID was
     * found
     */
    Set<TagData> findTags(Long testResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TagData tags} of a {@link TestResultData test result}
     * . The test result is identified by it's unique ID.
     *
     * @param testResultId the unique ID of the test result
     * @param filter the filter a tag has to match in order to be returned by
     * this method
     * @return all tags of the test result
     * @throws DataNotFoundException if no test result for the given ID was
     * found
     */
    Set<TagData> findTags(Long testResultId, Predicate<Tag> filter) throws DataNotFoundException;

    /**
     * Looks up the {@link TestResultDetailData details} of a
     * {@link TestResultData test result} . The test result is identified by
     * it's unique ID. The details are returned an chronologically sorted list.
     * THe first element of the list is the oldest detail.
     *
     * @param testResultId the unique ID of the test result
     * @return all details of the test result
     * @throws DataNotFoundException if no test result for the given ID was
     * found
     */
    List<TestResultDetailData> findTestResultDetails(Long testResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TestResultDetailData details} of a
     * {@link TestResultData test result} . The test result is identified by
     * it's unique ID. The details are returned an chronologically sorted list.
     * THe first element of the list is the oldest detail.
     *
     * @param testResultId the unique ID of the test result
     * @param filter the filter a detail has to match in order to be returned by
     * this method
     * @return all details of the test result
     * @throws DataNotFoundException if no test result for the given ID was
     * found
     */
    List<TestResultDetailData> findTestResultDetails(Long testResultId, Predicate<TestResultDetail> filter)
        throws DataNotFoundException;

}
