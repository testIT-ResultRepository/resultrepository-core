package info.novatec.testit.resultrepository.client.services;

import java.util.List;
import java.util.Set;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.TestResultsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link TestResultsRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class TestResultsClientService extends AbstractClientService implements TestResultsRemoteService {

    public TestResultsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public TestResultData findById(Long testResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TESTRESULT_FOR_ID, testResultId),
                TestResultData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestData findTest(Long testResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TEST_OF_TESTRESULT_FOR_ID, testResultId),
                TestData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestGroupResultData findTestGroupResult(Long testResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TESTGROUPRESULT_OF_TESTRESULT_FOR_ID, testResultId),
                TestGroupResultData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TagData> findTags(Long testResultId) throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TAGS_OF_TESTRESULT_FOR_ID, testResultId),
                TagData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public List<TestResultDetailData> findTestResultDetails(Long testResultId) throws RemoteOperationException {
        try {
            return asList(
                getTemplate().getForObject(buildUrl(V1ContextPaths.TESTRESULTDETAILS_OF_TESTRESULT_FOR_ID, testResultId),
                    TestResultDetailData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestResultStatistic calculateStatistic(Long testResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.STATISTIC_OF_TESTRESULT_FOR_ID, testResultId),
                TestResultStatistic.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
