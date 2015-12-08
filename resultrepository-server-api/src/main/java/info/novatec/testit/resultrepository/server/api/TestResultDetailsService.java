package info.novatec.testit.resultrepository.server.api;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding
 * {@link TestResultDetail test result details}.
 */
public interface TestResultDetailsService {

    /**
     * Looks up a {@link TestResultDetailData test result detail} by it's unique
     * ID.
     *
     * @param testResultDetailId the unique ID of the detail
     * @return the found detail
     * @throws DataNotFoundException if no detail for the given ID was found
     */
    TestResultDetailData findById(Long testResultDetailId) throws DataNotFoundException;

    /**
     * Looks up the {@link TestResultData test result} of a
     * {@link TestResultDetailData test result detail}. The detail is identified
     * by it's unique ID. If there is no link to a test result,
     * <code>null</code> is returned.
     *
     * @param testResultDetailId the unique ID of the detail
     * @return the found test result or <code>null</code>
     * @throws DataNotFoundException if no detail for the given ID was found
     */
    TestResultData findTestResult(Long testResultDetailId) throws DataNotFoundException;

}
