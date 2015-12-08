package info.novatec.testit.resultrepository.client.services;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.remote.v1.TestResultDetailsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link TestResultDetailsRemoteService} by calling the REST API of
 * a ResultRepository instance.
 */
public class TestResultDetailsClientService extends AbstractClientService implements TestResultDetailsRemoteService {

    public TestResultDetailsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public TestResultDetailData findById(Long testResultDetailId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TESTRESULTDETAIL_FOR_ID, testResultDetailId),
                TestResultDetailData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestResultData findTestResult(Long testResultDetailId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(
                buildUrl(V1ContextPaths.TESTRESULT_OF_TESTRESULTDETAIL_FOR_ID, testResultDetailId), TestResultData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
