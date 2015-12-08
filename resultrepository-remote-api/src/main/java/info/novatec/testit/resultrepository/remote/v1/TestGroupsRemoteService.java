package info.novatec.testit.resultrepository.remote.v1;

import java.util.Set;

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.TestGroup;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link TestGroup test groups}.
 *
 * @since 2.0.0
 */
public interface TestGroupsRemoteService {

    /**
     * Persists the given {@link TestGroupData test group} if it doesn't already
     * exist. It's existence is determined by its unique name.
     *
     * @param testGroup the test group to persist
     * @return the persisted test group containing updated information, like
     * it's unique ID, as a new instance
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestGroupData persist(TestGroupData testGroup) throws RemoteOperationException;

    /**
     * Returns all {@link TestGroupData test groups} from the repository.
     *
     * @return all test groups as a set
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestGroupData> findAll() throws RemoteOperationException;

    /**
     * Looks up a {@link TestGroupData test group} by it's unique ID.
     *
     * @param testGroupId the unique ID of the test group
     * @return the found test group
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestGroupData findById(Long testGroupId) throws RemoteOperationException;

    /**
     * Looks up a {@link TestGroupData test group} by it's unique ID and returns
     * all of it's {@link TestGroupResultData results}.
     *
     * @param testGroupId the unique ID of the test group
     * @return all results of the test group
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestGroupResultData> findTestGroupResults(Long testGroupId) throws RemoteOperationException;

}
