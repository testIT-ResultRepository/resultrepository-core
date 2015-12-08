package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILD_OF_TESTGROUPRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.STATISTIC_OF_TESTGROUPRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TAGS_OF_TESTGROUPRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUPRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUP_OF_TESTGROUPRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTRESULTS_OF_TESTGROUPRESULT_FOR_ID;
import static org.mockito.Mockito.doReturn;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;
import info.novatec.testit.resultrepository.server.api.StatisticsService;
import info.novatec.testit.resultrepository.server.api.TestGroupResultsService;


@RunWith(MockitoJUnitRunner.class)
public class TestGroupResultsControllerTest extends AbstractControllerTest {

    @Mock
    TestGroupResultsService delegateService;
    @Mock
    StatisticsService statisticsService;

    @InjectMocks
    TestGroupResultsController cut;

    @Override
    Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_findTestGroupResultById() throws Exception {

        TestGroupResultData result = new TestGroupResultData().setId(ID);
        doReturn(result).when(delegateService).findById(ID);

        performAndAssertGet(withId(TESTGROUPRESULT_FOR_ID, ID), result);

    }

    @Test
    public void testController_findTestGroupForTestGroupResultWithId() throws Exception {

        TestGroupData testGroup = new TestGroupData().setId(ID);
        doReturn(testGroup).when(delegateService).findTestGroup(ID);

        performAndAssertGet(withId(TESTGROUP_OF_TESTGROUPRESULT_FOR_ID, ID), testGroup);

    }

    @Test
    public void testController_findBuildForTestGroupResultWithId() throws Exception {

        BuildData build = new BuildData().setId(ID);
        doReturn(build).when(delegateService).findBuild(ID);

        performAndAssertGet(withId(BUILD_OF_TESTGROUPRESULT_FOR_ID, ID), build);

    }

    @Test
    public void testController_findTagsForTestGroupResultWithId() throws Exception {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        Set<TagData> tags = Sets.newSet(tag1, tag2);
        doReturn(tags).when(delegateService).findTags(ID);

        performAndAssertGet(withId(TAGS_OF_TESTGROUPRESULT_FOR_ID, ID), tags);

    }

    @Test
    public void testController_findTestResultsForTestGroupResultWithId() throws Exception {

        TestResultData result1 = new TestResultData().setId(ID);
        TestResultData result2 = new TestResultData().setId(ANOTHER_ID);
        Set<TestResultData> results = Sets.newSet(result1, result2);
        doReturn(results).when(delegateService).findTestResults(ID);

        performAndAssertGet(withId(TESTRESULTS_OF_TESTGROUPRESULT_FOR_ID, ID), results);

    }

    @Test
    public void testController_calculateStatistic() throws Exception {

        TestGroupResultStatistic statistic = new TestGroupResultStatistic();
        doReturn(statistic).when(statisticsService).calculateTestGroupResultStatistic(ID);

        performAndAssertGet(withId(STATISTIC_OF_TESTGROUPRESULT_FOR_ID, ID), statistic);

    }

}
