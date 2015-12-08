package info.novatec.testit.resultrepository.client.services;

import java.util.Set;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.remote.v1.TestsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link TestsRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class TestsClientService extends AbstractClientService implements TestsRemoteService {

    public TestsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public TestData persist(TestData test) throws RemoteOperationException {
        try {
            return getTemplate().postForObject(buildUrl(V1ContextPaths.TESTS), test, TestData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestData> findAll() throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TESTS), TestData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestData findById(Long testId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TEST_FOR_ID, testId), TestData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestResultData> findTestResults(Long testId) throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TESTRESULTS_OF_TEST_FOR_ID, testId),
                TestResultData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
