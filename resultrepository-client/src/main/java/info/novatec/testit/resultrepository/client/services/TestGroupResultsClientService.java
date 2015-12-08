package info.novatec.testit.resultrepository.client.services;

import java.util.Set;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.TestGroupResultsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link TestGroupResultsRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class TestGroupResultsClientService extends AbstractClientService implements TestGroupResultsRemoteService {

    public TestGroupResultsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public TestGroupResultData findById(Long testGroupResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TESTGROUPRESULT_FOR_ID, testGroupResultId),
                TestGroupResultData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestGroupData findTestGroup(Long testGroupResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(
                buildUrl(V1ContextPaths.TESTGROUP_OF_TESTGROUPRESULT_FOR_ID, testGroupResultId), TestGroupData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public BuildData findBuild(Long testGroupResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.BUILD_OF_TESTGROUPRESULT_FOR_ID, testGroupResultId),
                BuildData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TagData> findTags(Long testGroupResultId) throws RemoteOperationException {
        try {
            return asSet(
                getTemplate().getForObject(buildUrl(V1ContextPaths.TAGS_OF_TESTGROUPRESULT_FOR_ID, testGroupResultId),
                    TagData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestResultData> findTestResults(Long testGroupResultId) throws RemoteOperationException {
        try {
            return asSet(
                getTemplate().getForObject(buildUrl(V1ContextPaths.TESTRESULTS_OF_TESTGROUPRESULT_FOR_ID, testGroupResultId),
                    TestResultData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestGroupResultStatistic calculateStatistic(Long testGroupResultId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(
                buildUrl(V1ContextPaths.STATISTIC_OF_TESTGROUPRESULT_FOR_ID, testGroupResultId),
                TestGroupResultStatistic.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
