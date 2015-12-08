package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.IMPORT_TEST_GROUP_RESULT;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.server.api.AsynchronousImportService;


@RunWith(MockitoJUnitRunner.class)
public class ImportControllerTest extends AbstractControllerTest {

    @Mock
    AsynchronousImportService importService;

    @InjectMocks
    ImportController cut;

    @Override
    protected Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testThatControllerDelegatesToService() throws Exception {

        TestGroupResultData result = new TestGroupResultData().setId(ID);

        performAndAssertPost(IMPORT_TEST_GROUP_RESULT, result);

        verify(importService).importResult(eq(result));
        verifyNoMoreInteractions(importService);

    }

}
