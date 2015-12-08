package info.novatec.testit.resultrepository.client.services;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class ImportClientServiceTest extends AbstractClientServiceTest<ImportClientService> {

    @Override
    ImportClientService createClassUnderTest(String baseUrl) {
        return new ImportClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_importResult() {

        TestGroupResultData result = new TestGroupResultData().setId(ID);
        setupPostForObjectWithoutResponseExpectations(V1ContextPaths.IMPORT_TEST_GROUP_RESULT, result);

        cut.importResult(result);

        mockServer.verify();

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_importResult_exception() {
        TestGroupResultData result = new TestGroupResultData().setId(ID);
        setupPostExceptionExpectations(V1ContextPaths.IMPORT_TEST_GROUP_RESULT, result);
        try {
            cut.importResult(result);
        } finally {
            mockServer.verify();
        }
    }

}
