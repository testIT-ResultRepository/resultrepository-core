package info.novatec.testit.resultrepository.remote.v1;

import java.util.Set;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Test;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link Test tests}.
 *
 * @since 2.0.0
 */
public interface TestsRemoteService {

    /**
     * Persists the given {@link TestData test} if it doesn't already exist.
     * It's existence is determined by its unique name.
     *
     * @param test the test to persist
     * @return the persisted test containing updated information, like it's
     * unique ID, as a new instance
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestData persist(TestData test) throws RemoteOperationException;

    /**
     * Returns all {@link TestData tests} from the repository.
     *
     * @return all tests as a set
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestData> findAll() throws RemoteOperationException;

    /**
     * Looks up a {@link TestData test} by it's unique ID.
     *
     * @param testId the unique ID of the test
     * @return the found test
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestData findById(Long testId) throws RemoteOperationException;

    /**
     * Looks up a {@link TestData test} by it's unique ID and returns all of
     * it's {@link TestResultData results}.
     *
     * @param testId the unique ID of the test
     * @return all results of the test
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestResultData> findTestResults(Long testId) throws RemoteOperationException;

}
