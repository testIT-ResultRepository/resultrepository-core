package info.novatec.testit.resultrepository.client.services;

import java.util.Set;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.BuildsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link BuildsRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class BuildsClientService extends AbstractClientService implements BuildsRemoteService {

    public BuildsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public BuildData persist(BuildData build) throws RemoteOperationException {
        try {
            return getTemplate().postForObject(buildUrl(V1ContextPaths.BUILDS), build, BuildData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public BuildData findById(Long buildId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.BUILD_FOR_ID, buildId), BuildData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public BuildJobData findBuildJob(Long buildId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.BUILDJOB_OF_BUILD_FOR_ID, buildId),
                BuildJobData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TagData> findTags(Long buildId) throws RemoteOperationException {
        try {
            return asSet(
                getTemplate().getForObject(buildUrl(V1ContextPaths.TAGS_OF_BUILD_FOR_ID, buildId), TagData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestGroupResultData> findTestGroupResults(Long buildId) throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TESTGROUPRESULTS_OF_BUILD_FOR_ID, buildId),
                TestGroupResultData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public BuildResultStatistic calculateStatistic(Long buildId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.STATISTIC_OF_BUILD_FOR_ID, buildId),
                BuildResultStatistic.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
