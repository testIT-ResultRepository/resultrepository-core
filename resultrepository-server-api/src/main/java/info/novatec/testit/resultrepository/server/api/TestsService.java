package info.novatec.testit.resultrepository.server.api;

import java.util.Set;
import java.util.function.Predicate;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Test;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding
 * {@link Test tests}.
 */
public interface TestsService {

    /**
     * Persists the given {@link TestData test} if it doesn't already exist.
     * It's existence is determined by its unique name.
     *
     * @param test the test to persist
     * @return the persisted test containing updated information, like it's
     * unique ID, as a new instance
     */
    TestData persist(TestData test);

    /**
     * Returns all {@link TestData tests} from the repository.
     *
     * @return all tests as a set
     */
    Set<TestData> findAll();

    /**
     * Returns all {@link TestData tests}, which match the given filter, from
     * the repository.
     *
     * @param filter the filter a test has to match in order to be returned by
     * this method
     * @return all matching tests as a set
     */
    Set<TestData> findAll(Predicate<Test> filter);

    /**
     * Looks up a {@link TestData test} by it's unique ID.
     *
     * @param testId the unique ID of the test
     * @return the found test
     * @throws DataNotFoundException if no test for the given ID was found
     */
    TestData findById(Long testId) throws DataNotFoundException;

    /**
     * Looks up a {@link TestData test} by it's unique ID and returns all of
     * it's {@link TestResultData results}.
     *
     * @param testId the unique ID of the test
     * @return all results of the test
     * @throws DataNotFoundException if no test for the given ID was found
     */
    Set<TestResultData> findTestResults(Long testId) throws DataNotFoundException;

    /**
     * Looks up a {@link TestData test} by it's unique ID and returns all of
     * it's {@link TestResultData results}.
     *
     * @param testId the unique ID of the test
     * @param filter the filter a test result has to match in order to be
     * returned by this method
     * @return all results of the test
     * @throws DataNotFoundException if no test for the given ID was found
     */
    Set<TestResultData> findTestResults(Long testId, Predicate<TestResult> filter) throws DataNotFoundException;

}
