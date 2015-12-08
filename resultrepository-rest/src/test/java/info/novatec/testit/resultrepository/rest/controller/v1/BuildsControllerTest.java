package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILDJOB_OF_BUILD_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILDS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILD_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.STATISTIC_OF_BUILD_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TAGS_OF_BUILD_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUPRESULTS_OF_BUILD_FOR_ID;
import static org.mockito.Mockito.doReturn;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;
import info.novatec.testit.resultrepository.server.api.BuildsService;
import info.novatec.testit.resultrepository.server.api.StatisticsService;


@RunWith(MockitoJUnitRunner.class)
public class BuildsControllerTest extends AbstractControllerTest {

    @Mock
    BuildsService delegateService;
    @Mock
    StatisticsService statisticsService;

    @InjectMocks
    BuildsController cut;

    @Override
    protected Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_persist() throws Exception {

        BuildJobData inputBuildJob = new BuildJobData().setName("buildJob");
        BuildData inputBuild = new BuildData().setBuildJob(inputBuildJob).setBuildNumber(1);
        BuildJobData outputBuildJob = new BuildJobData(inputBuildJob).setId(ID);
        BuildData outputBuild = new BuildData(inputBuild).setBuildJob(outputBuildJob).setId(ANOTHER_ID);
        doReturn(outputBuild).when(delegateService).persist(inputBuild);

        performAndAssertPost(BUILDS, inputBuild, outputBuild);

    }

    @Test
    public void testController_findBuildById() throws Exception {

        BuildData build = new BuildData().setId(ID);
        doReturn(build).when(delegateService).findById(ID);

        performAndAssertGet(withId(BUILD_FOR_ID, ID), build);

    }

    @Test
    public void testController_findBuildJobForBuildWithId() throws Exception {

        BuildJobData buildJob = new BuildJobData().setId(ID);
        doReturn(buildJob).when(delegateService).findBuildJob(ID);

        performAndAssertGet(withId(BUILDJOB_OF_BUILD_FOR_ID, ID), buildJob);

    }

    @Test
    public void testController_findTagsForBuildWithId() throws Exception {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        Set<TagData> tags = Sets.newSet(tag1, tag2);
        doReturn(tags).when(delegateService).findTags(ID);

        performAndAssertGet(withId(TAGS_OF_BUILD_FOR_ID, ID), tags);

    }

    @Test
    public void testController_findTestGroupResultsForBuildWithId() throws Exception {

        TestGroupResultData result1 = new TestGroupResultData().setId(ID);
        TestGroupResultData result2 = new TestGroupResultData().setId(ANOTHER_ID);
        Set<TestGroupResultData> results = Sets.newSet(result1, result2);
        doReturn(results).when(delegateService).findTestGroupResults(ID);

        performAndAssertGet(withId(TESTGROUPRESULTS_OF_BUILD_FOR_ID, ID), results);

    }

    @Test
    public void testController_calculateStatistic() throws Exception {

        BuildResultStatistic statistic = new BuildResultStatistic();
        doReturn(statistic).when(statisticsService).calculateBuildResultStatistic(ID);

        performAndAssertGet(withId(STATISTIC_OF_BUILD_FOR_ID, ID), statistic);

    }

}
