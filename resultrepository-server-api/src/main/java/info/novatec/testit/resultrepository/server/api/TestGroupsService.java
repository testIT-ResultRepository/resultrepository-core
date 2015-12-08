package info.novatec.testit.resultrepository.server.api;

import java.util.Set;
import java.util.function.Predicate;

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.TestGroup;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding
 * {@link TestGroup test groups}.
 */
public interface TestGroupsService {

    /**
     * Persists the given {@link TestGroupData test group} if it doesn't already
     * exist. It's existence is determined by its unique name.
     *
     * @param testGroup the test group to persist
     * @return the persisted test group containing updated information, like
     * it's unique ID, as a new instance
     */
    TestGroupData persist(TestGroupData testGroup);

    /**
     * Returns all {@link TestGroupData test groups} from the repository.
     *
     * @return all test groups as a set
     */
    Set<TestGroupData> findAll();

    /**
     * Returns all {@link TestGroupData test groups}, which match the given
     * filter, from the repository.
     *
     * @param filter the filter a test group has to match in order to be
     * returned by this method
     * @return all matching test groups as a set
     */
    Set<TestGroupData> findAll(Predicate<TestGroup> filter);

    /**
     * Looks up a {@link TestGroupData test group} by it's unique ID.
     *
     * @param testGroupId the unique ID of the test group
     * @return the found test group
     * @throws DataNotFoundException if no test group for the given ID was found
     */
    TestGroupData findById(Long testGroupId) throws DataNotFoundException;

    /**
     * Looks up a {@link TestGroupData test group} by it's unique ID and returns
     * all of it's {@link TestGroupResultData results}.
     *
     * @param testGroupId the unique ID of the test group
     * @return all results of the test group
     * @throws DataNotFoundException if no test group for the given ID was found
     */
    Set<TestGroupResultData> findTestGroupResults(Long testGroupId) throws DataNotFoundException;

    /**
     * Looks up a {@link TestGroupData test group} by it's unique ID and returns
     * all of it's {@link TestGroupResultData results}.
     *
     * @param testGroupId the unique ID of the test group
     * @param filter the filter a result has to match in order to be returned by
     * this method
     * @return all results of the test group
     * @throws DataNotFoundException if no test group for the given ID was found
     */
    Set<TestGroupResultData> findTestGroupResults(Long testGroupId, Predicate<TestGroupResult> filter)
        throws DataNotFoundException;

}
