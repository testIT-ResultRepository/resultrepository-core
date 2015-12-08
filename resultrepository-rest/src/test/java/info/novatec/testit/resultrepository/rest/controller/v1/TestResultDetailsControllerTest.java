package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTRESULTDETAIL_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTRESULT_OF_TESTRESULTDETAIL_FOR_ID;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.server.api.TestResultDetailsService;


@RunWith(MockitoJUnitRunner.class)
public class TestResultDetailsControllerTest extends AbstractControllerTest {

    @Mock
    TestResultDetailsService delegateService;

    @InjectMocks
    TestResultDetailsController cut;

    @Override
    Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_findTestResultDetailById() throws Exception {

        TestResultDetailData detail = new TestResultDetailData().setId(ID);
        doReturn(detail).when(delegateService).findById(ID);

        performAndAssertGet(withId(TESTRESULTDETAIL_FOR_ID, ID), detail);

    }

    @Test
    public void testController_findTestResultForTestResultDetailWithId() throws Exception {

        TestResultData result = new TestResultData().setId(ID);
        doReturn(result).when(delegateService).findTestResult(ID);

        performAndAssertGet(withId(TESTRESULT_OF_TESTRESULTDETAIL_FOR_ID, ID), result);

    }

}
