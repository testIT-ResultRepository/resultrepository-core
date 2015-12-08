package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILDJOBS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILDJOB_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILDS_OF_BUILDJOB_FOR_ID;
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
import info.novatec.testit.resultrepository.server.api.BuildJobsService;


@RunWith(MockitoJUnitRunner.class)
public class BuildJobsControllerTest extends AbstractControllerTest {

    @Mock
    BuildJobsService delegateService;

    @InjectMocks
    BuildJobsController cut;

    @Override
    protected Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_persist() throws Exception {

        BuildJobData inputBuildJob = new BuildJobData();
        BuildJobData outputBuildJob = new BuildJobData().setId(ID);
        doReturn(outputBuildJob).when(delegateService).persist(inputBuildJob);

        performAndAssertPost(BUILDJOBS, inputBuildJob, outputBuildJob);

    }

    @Test
    public void testController_findAllBuildJobs() throws Exception {

        BuildJobData buildJob1 = new BuildJobData().setId(ID);
        BuildJobData buildJob2 = new BuildJobData().setId(ANOTHER_ID);
        Set<BuildJobData> buildJobs = Sets.newSet(buildJob1, buildJob2);
        doReturn(buildJobs).when(delegateService).findAll();

        performAndAssertGet(BUILDJOBS, buildJobs);

    }

    @Test
    public void testController_findBuildJobById() throws Exception {

        BuildJobData buildJob = new BuildJobData().setId(ID);
        doReturn(buildJob).when(delegateService).findById(ID);

        performAndAssertGet(withId(BUILDJOB_FOR_ID, ID), buildJob);

    }

    @Test
    public void testController_findBuildsForBuildJobWithId() throws Exception {

        BuildData build1 = new BuildData().setId(ID);
        BuildData build2 = new BuildData().setId(ANOTHER_ID);
        Set<BuildData> builds = Sets.newSet(build1, build2);
        doReturn(builds).when(delegateService).findBuilds(ID);

        performAndAssertGet(withId(BUILDS_OF_BUILDJOB_FOR_ID, ID), builds);

    }

}
