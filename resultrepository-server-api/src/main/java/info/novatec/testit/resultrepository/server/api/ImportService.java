package info.novatec.testit.resultrepository.server.api;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;


/**
 * Services implementing this interface provide operations regarding the import
 * of data into the ResultRepository.
 */
public interface ImportService {

    /**
     * Imports a single {@linkplain TestGroupResultData test group result} and
     * all its associated data.
     *
     * @param result the result to import.
     */
    void importResult(TestGroupResultData result);

}
