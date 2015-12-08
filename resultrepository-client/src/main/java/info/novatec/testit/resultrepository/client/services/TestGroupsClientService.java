package info.novatec.testit.resultrepository.client.services;

import java.util.Set;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.remote.v1.TestGroupsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link TestGroupsRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class TestGroupsClientService extends AbstractClientService implements TestGroupsRemoteService {

    public TestGroupsClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public TestGroupData persist(TestGroupData testGroup) throws RemoteOperationException {
        try {
            return getTemplate().postForObject(buildUrl(V1ContextPaths.TESTGROUPS), testGroup, TestGroupData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestGroupData> findAll() throws RemoteOperationException {
        try {
            return asSet(getTemplate().getForObject(buildUrl(V1ContextPaths.TESTGROUPS), TestGroupData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public TestGroupData findById(Long testGroupId) throws RemoteOperationException {
        try {
            return getTemplate().getForObject(buildUrl(V1ContextPaths.TESTGROUP_FOR_ID, testGroupId), TestGroupData.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

    @Override
    public Set<TestGroupResultData> findTestGroupResults(Long testGroupId) throws RemoteOperationException {
        try {
            return asSet(
                getTemplate().getForObject(buildUrl(V1ContextPaths.TESTGROUPRESULTS_OF_TESTGROUP_FOR_ID, testGroupId),
                    TestGroupResultData[].class));
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }

}
