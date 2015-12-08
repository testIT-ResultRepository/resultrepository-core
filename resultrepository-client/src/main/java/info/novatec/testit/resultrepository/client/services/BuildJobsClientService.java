package info.novatec.testit.resultrepository.client.services;

import java.util.Set;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.remote.v1.BuildJobsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link BuildJobsRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class BuildJobsClientService extends AbstractClientService implements BuildJobsRemoteService {

    public BuildJobsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public BuildJobData persist(BuildJobData buildJob) throws RemoteOperationException {
        try {
            return getTemplate().postForObject(buildUrl(V1ContextPaths.BUILDJOBS), buildJob, BuildJobData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<BuildJobData> findAll() throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.BUILDJOBS), BuildJobData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public BuildJobData findById(Long buildJobId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.BUILDJOB_FOR_ID, buildJobId), BuildJobData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<BuildData> findBuilds(Long buildJobId) throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.BUILDS_OF_BUILDJOB_FOR_ID, buildJobId),
                BuildData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
