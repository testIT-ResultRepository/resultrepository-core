package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class BuildsClientServiceTest extends AbstractClientServiceTest<BuildsClientService> {

    @Override
    BuildsClientService createClassUnderTest(String baseUrl) {
        return new BuildsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_persist() {

        BuildJobData buildJob = new BuildJobData().setName("buildJob");
        BuildData build = new BuildData().setBuildJob(buildJob).setBuildNumber(1);
        BuildJobData expectedBuildJob =
            new BuildJobData().setName("buildJob").setId(ANOTHER_ID).setCreationTimestamp(System.currentTimeMillis());
        BuildData expected = new BuildData().setBuildJob(expectedBuildJob)
            .setBuildNumber(1)
            .setId(ID)
            .setCreationTimestamp(System.currentTimeMillis());
        setupPostForObjectExpectations(V1ContextPaths.BUILDS, build, expected);

        BuildData persisted = cut.persist(build);

        mockServer.verify();
        assertThat(persisted).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_persist_exception() {
        BuildJobData buildJob = new BuildJobData().setName("buildJob");
        BuildData build = new BuildData().setBuildJob(buildJob).setBuildNumber(1);
        setupPostExceptionExpectations(V1ContextPaths.BUILDS, build);
        try {
            cut.persist(build);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        BuildData expected = new BuildData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.BUILD_FOR_ID, ID), expected);

        BuildData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.BUILD_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findBuildJob() {

        BuildJobData expected = new BuildJobData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.BUILDJOB_OF_BUILD_FOR_ID, ID), expected);

        BuildJobData found = cut.findBuildJob(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findBuildJob_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.BUILDJOB_OF_BUILD_FOR_ID, ID));
        try {
            cut.findBuildJob(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTags() {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TAGS_OF_BUILD_FOR_ID, ID), Arrays.asList(tag1, tag2));

        Set<TagData> found = cut.findTags(ID);

        mockServer.verify();
        assertThat(found).containsOnly(tag1, tag2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTags_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TAGS_OF_BUILD_FOR_ID, ID));
        try {
            cut.findTags(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestGroupResults() {

        TestGroupResultData result1 = new TestGroupResultData().setId(ID);
        TestGroupResultData result2 = new TestGroupResultData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTGROUPRESULTS_OF_BUILD_FOR_ID, ID),
            Arrays.asList(result1, result2));

        Set<TestGroupResultData> found = cut.findTestGroupResults(ID);

        mockServer.verify();
        assertThat(found).containsOnly(result1, result2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestGroupResults_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTGROUPRESULTS_OF_BUILD_FOR_ID, ID));
        try {
            cut.findTestGroupResults(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_calculateStatistic() {

        BuildResultStatistic expected = new BuildResultStatistic();
        expected.setBuildId(42L);

        setupGetForObjectExpectations(withId(V1ContextPaths.STATISTIC_OF_BUILD_FOR_ID, ID), expected);

        BuildResultStatistic found = cut.calculateStatistic(ID);

        mockServer.verify();
        assertThat(found).isEqualToComparingFieldByField(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_calculateStatistic_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.STATISTIC_OF_BUILD_FOR_ID, ID));
        try {
            cut.calculateStatistic(ID);
        } finally {
            mockServer.verify();
        }
    }

}
