package info.novatec.testit.resultrepository.server.api;

import java.util.Set;
import java.util.function.Predicate;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding
 * {@link TestGroupResult test group results}.
 */
public interface TestGroupResultsService {

    /**
     * Looks up a {@link TestGroupResultData test group result} by it's unique
     * ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return the found test group result
     * @throws DataNotFoundException if no test group result for the given ID
     * was found
     */
    TestGroupResultData findById(Long testGroupResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TestGroupData test group} of a
     * {@link TestGroupResultData test group result}. The test group result is
     * identified by it's unique ID. If there is no link to a test group,
     * <code>null</code> is returned.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return the found test group or <code>null</code>
     * @throws DataNotFoundException if no test group result for the given ID
     * was found
     */
    TestGroupData findTestGroup(Long testGroupResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link BuildData build} of a {@link TestGroupResultData test
     * group result}. The test group result is identified by it's unique ID. If
     * there is no link to a build, <code>null</code> is returned.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return the found build or <code>null</code>
     * @throws DataNotFoundException if no test group result for the given ID
     * was found
     */
    BuildData findBuild(Long testGroupResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TagData tags} of a {@link TestGroupResultData test
     * group result} . The test group result is identified by it's unique ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return all tags of the test group result
     * @throws DataNotFoundException if no test group result for the given ID
     * was found
     */
    Set<TagData> findTags(Long testGroupResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TagData tags} of a {@link TestGroupResultData test
     * group result} . The test group result is identified by it's unique ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @param filter the filter a tag has to match in order to be returned by
     * this method
     * @return all tags of the test group result
     * @throws DataNotFoundException if no test group result for the given ID
     * was found
     */
    Set<TagData> findTags(Long testGroupResultId, Predicate<Tag> filter) throws DataNotFoundException;

    /**
     * Looks up the {@link TestResultData test results} of a
     * {@link TestGroupResultData test group result} . The test group result is
     * identified by it's unique ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @return all test results of the test group result
     * @throws DataNotFoundException if no test group result for the given ID
     * was found
     */
    Set<TestResultData> findTestResults(Long testGroupResultId) throws DataNotFoundException;

    /**
     * Looks up the {@link TestResultData test results} of a
     * {@link TestGroupResultData test group result} . The test group result is
     * identified by it's unique ID.
     *
     * @param testGroupResultId the unique ID of the test group result
     * @param filter the filter a test result has to match in order to be
     * returned by this method
     * @return all test results of the test group result
     * @throws DataNotFoundException if no test group result for the given ID
     * was found
     */
    Set<TestResultData> findTestResults(Long testGroupResultId, Predicate<TestResult> filter) throws DataNotFoundException;

}
