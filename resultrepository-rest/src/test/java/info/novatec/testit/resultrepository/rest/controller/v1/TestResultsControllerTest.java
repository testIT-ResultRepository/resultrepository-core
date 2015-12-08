package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.STATISTIC_OF_TESTRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TAGS_OF_TESTRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUPRESULT_OF_TESTRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTRESULTDETAILS_OF_TESTRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTRESULT_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TEST_OF_TESTRESULT_FOR_ID;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;
import info.novatec.testit.resultrepository.server.api.StatisticsService;
import info.novatec.testit.resultrepository.server.api.TestResultsService;


@RunWith(MockitoJUnitRunner.class)
public class TestResultsControllerTest extends AbstractControllerTest {

    @Mock
    TestResultsService delegateService;
    @Mock
    StatisticsService statisticsService;

    @InjectMocks
    TestResultsController cut;

    @Override
    Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_findTestResultById() throws Exception {

        TestResultData result = new TestResultData().setId(ID);
        doReturn(result).when(delegateService).findById(ID);

        performAndAssertGet(withId(TESTRESULT_FOR_ID, ID), result);

    }

    @Test
    public void testController_findTestForTestResultWithId() throws Exception {

        TestData test = new TestData().setId(ID);
        doReturn(test).when(delegateService).findTest(ID);

        performAndAssertGet(withId(TEST_OF_TESTRESULT_FOR_ID, ID), test);

    }

    @Test
    public void testController_findTestGroupResultForTestResultWithId() throws Exception {

        TestGroupResultData result = new TestGroupResultData().setId(ID);
        doReturn(result).when(delegateService).findTestGroupResult(ID);

        performAndAssertGet(withId(TESTGROUPRESULT_OF_TESTRESULT_FOR_ID, ID), result);

    }

    @Test
    public void testController_findTagsForTestResultWithId() throws Exception {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        Set<TagData> tags = Sets.newSet(tag1, tag2);
        doReturn(tags).when(delegateService).findTags(ID);

        performAndAssertGet(withId(TAGS_OF_TESTRESULT_FOR_ID, ID), tags);

    }

    @Test
    public void testController_findTestResultDetailsForTestResultWithId() throws Exception {

        TestResultDetailData detail1 = new TestResultDetailData().setId(ID);
        TestResultDetailData detail2 = new TestResultDetailData().setId(ANOTHER_ID);
        List<TestResultDetailData> details = Arrays.asList(detail1, detail2);
        doReturn(details).when(delegateService).findTestResultDetails(ID);

        performAndAssertGet(withId(TESTRESULTDETAILS_OF_TESTRESULT_FOR_ID, ID), details);

    }

    @Test
    public void testController_calculateStatistic() throws Exception {

        TestResultStatistic statistic = new TestResultStatistic();
        doReturn(statistic).when(statisticsService).calculateTestResultStatistic(ID);

        performAndAssertGet(withId(STATISTIC_OF_TESTRESULT_FOR_ID, ID), statistic);

    }

}
