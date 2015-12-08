package info.novatec.testit.resultrepository.client.services;

import java.util.Set;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.remote.v1.TagsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.descriptors.TagDescriptor;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link TagsRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class TagsClientService extends AbstractClientService implements TagsRemoteService {

    public TagsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public TagData persist(TagData tag) throws RemoteOperationException {
        try {
            return getTemplate().postForObject(buildUrl(V1ContextPaths.TAGS), tag, TagData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TagData> findAll() throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TAGS), TagData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TagData> findMatching(TagDescriptor descriptor) throws RemoteOperationException {
        try {
            return asSet(getTemplate().postForObject(buildUrl(V1ContextPaths.TAGS_SEARCH), descriptor, TagData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TagData findById(Long tagId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TAG_FOR_ID, tagId), TagData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<BuildData> findBuilds(Long tagId) throws RemoteOperationException {
        try {
            return asSet(
                getTemplate().getForObject(buildUrl(V1ContextPaths.BUILDS_OF_TAG_FOR_ID, tagId), BuildData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestGroupResultData> findTestGroupResults(Long tagId) throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TESTGROUPRESULTS_OF_TAG_FOR_ID, tagId),
                TestGroupResultData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestResultData> findTestResults(Long tagId) throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TESTRESULTS_OF_TAG_FOR_ID, tagId),
                TestResultData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
