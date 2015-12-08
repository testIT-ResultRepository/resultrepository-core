package info.novatec.testit.resultrepository.client.services;

import org.springframework.web.client.RestClientException;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.remote.v1.ImportRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Implements {@link ImportRemoteService} by calling the REST API of a
 * ResultRepository instance.
 */
public class ImportClientService extends AbstractClientService implements ImportRemoteService {

    public ImportClientService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public void importResult(TestGroupResultData result) throws RemoteOperationException {
        try {
            getTemplate().postForObject(buildUrl(V1ContextPaths.IMPORT_TEST_GROUP_RESULT), result, String.class);
        } catch (RestClientException e) {
            throw new RemoteOperationException(e);
        }
    }
}
