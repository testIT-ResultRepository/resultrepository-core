package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUPRESULTS_OF_TESTGROUP_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUPS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUP_FOR_ID;
import static org.mockito.Mockito.doReturn;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.server.api.TestGroupsService;


@RunWith(MockitoJUnitRunner.class)
public class TestGroupsControllerTest extends AbstractControllerTest {

    @Mock
    TestGroupsService delegateService;

    @InjectMocks
    TestGroupsController cut;

    @Override
    protected Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_persist() throws Exception {

        TestGroupData inputTestGroup = new TestGroupData().setName("testGroup").setCreationTimestamp(100L);
        TestGroupData outputTestGroup = new TestGroupData(inputTestGroup).setId(ID);
        doReturn(outputTestGroup).when(delegateService).persist(inputTestGroup);

        performAndAssertPost(TESTGROUPS, inputTestGroup, outputTestGroup);

    }

    @Test
    public void testController_findAllTestGroups() throws Exception {

        TestGroupData testGroup1 = new TestGroupData().setId(ID);
        TestGroupData testGroup2 = new TestGroupData().setId(ANOTHER_ID);
        Set<TestGroupData> testGroups = Sets.newSet(testGroup1, testGroup2);
        doReturn(testGroups).when(delegateService).findAll();

        performAndAssertGet(TESTGROUPS, testGroups);

    }

    @Test
    public void testController_findTestGroupById() throws Exception {

        TestGroupData testGroup = new TestGroupData().setId(ID);
        doReturn(testGroup).when(delegateService).findById(ID);

        performAndAssertGet(withId(TESTGROUP_FOR_ID, ID), testGroup);

    }

    @Test
    public void testController_findTestGroupResultsForTestGroupWithId() throws Exception {

        TestGroupResultData result1 = new TestGroupResultData().setId(ID);
        TestGroupResultData result2 = new TestGroupResultData().setId(ANOTHER_ID);
        Set<TestGroupResultData> results = Sets.newSet(result1, result2);
        doReturn(results).when(delegateService).findTestGroupResults(ID);

        performAndAssertGet(withId(TESTGROUPRESULTS_OF_TESTGROUP_FOR_ID, ID), results);

    }

}
