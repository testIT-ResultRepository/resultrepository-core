package info.novatec.testit.resultrepository.server.api;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;


/**
 * Services implementing this interface can be used to asynchronously import
 * data into the repository.
 */
public interface AsynchronousImportService {

    /**
     * Imports a single {@linkplain TestGroupResultData test group result}. If
     * this method returns without exception the result was delivered to the
     * repository for further processing. This does not mean it was successfully
     * persisted!
     *
     * @param result the result to import.
     */
    void importResult(TestGroupResultData result);

}
