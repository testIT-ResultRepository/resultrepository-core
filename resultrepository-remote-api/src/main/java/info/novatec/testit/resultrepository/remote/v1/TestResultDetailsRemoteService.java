package info.novatec.testit.resultrepository.remote.v1;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link TestResultDetail test result details}.
 *
 * @since 2.0.0
 */
public interface TestResultDetailsRemoteService {

    /**
     * Looks up a {@link TestResultDetailData test result detail} by it's unique
     * ID.
     *
     * @param testResultDetailId the unique ID of the detail
     * @return the found detail
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestResultDetailData findById(Long testResultDetailId) throws RemoteOperationException;

    /**
     * Looks up the {@link TestResultData test result} of a
     * {@link TestResultDetailData test result detail}. The detail is identified
     * by it's unique ID. If there is no link to a test result,
     * <code>null</code> is returned.
     *
     * @param testResultDetailId the unique ID of the detail
     * @return the found test result or <code>null</code>
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TestResultData findTestResult(Long testResultDetailId) throws RemoteOperationException;

}
