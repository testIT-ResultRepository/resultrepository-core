package info.novatec.testit.resultrepository.remote.v1;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding the
 * import of data into the ResultRepository.
 *
 * @since 2.0.0
 */
public interface ImportRemoteService {

    /**
     * Imports a single {@linkplain TestGroupResultData test group result} and
     * all its associated data.
     *
     * @param result the result to import.
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    void importResult(TestGroupResultData result) throws RemoteOperationException;

}
