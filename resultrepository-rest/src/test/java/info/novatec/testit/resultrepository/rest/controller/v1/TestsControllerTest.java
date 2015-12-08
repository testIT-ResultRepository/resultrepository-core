package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTRESULTS_OF_TEST_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TEST_FOR_ID;
import static org.mockito.Mockito.doReturn;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.server.api.TestsService;


@RunWith(MockitoJUnitRunner.class)
public class TestsControllerTest extends AbstractControllerTest {

    @Mock
    TestsService delegateService;

    @InjectMocks
    TestsController cut;

    @Override
    protected Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_persist() throws Exception {

        TestData inputTest = new TestData().setName("test").setCreationTimestamp(100L);
        TestData outputTest = new TestData(inputTest).setId(ID);
        doReturn(outputTest).when(delegateService).persist(inputTest);

        performAndAssertPost(TESTS, inputTest, outputTest);

    }

    @Test
    public void testController_findAllTests() throws Exception {

        TestData test1 = new TestData().setId(ID);
        TestData test2 = new TestData().setId(ANOTHER_ID);
        Set<TestData> tests = Sets.newSet(test1, test2);
        doReturn(tests).when(delegateService).findAll();

        performAndAssertGet(TESTS, tests);

    }

    @Test
    public void testController_findTestById() throws Exception {

        TestData test = new TestData().setId(ID);
        doReturn(test).when(delegateService).findById(ID);

        performAndAssertGet(withId(TEST_FOR_ID, ID), test);

    }

    @Test
    public void testController_findTestResultsForTestWithId() throws Exception {

        TestResultData result1 = new TestResultData().setId(ID);
        TestResultData result2 = new TestResultData().setId(ANOTHER_ID);
        Set<TestResultData> results = Sets.newSet(result1, result2);
        doReturn(results).when(delegateService).findTestResults(ID);

        performAndAssertGet(withId(TESTRESULTS_OF_TEST_FOR_ID, ID), results);

    }

}
