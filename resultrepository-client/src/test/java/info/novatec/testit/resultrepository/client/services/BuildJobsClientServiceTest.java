package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class BuildJobsClientServiceTest extends AbstractClientServiceTest<BuildJobsClientService> {

    @Override
    BuildJobsClientService createClassUnderTest(String baseUrl) {
        return new BuildJobsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_persist() {

        BuildJobData buildJob = new BuildJobData().setName("buildJob");
        BuildJobData expected =
            new BuildJobData().setName("buildJob").setId(ID).setCreationTimestamp(System.currentTimeMillis());
        setupPostForObjectExpectations(V1ContextPaths.BUILDJOBS, buildJob, expected);

        BuildJobData persisted = cut.persist(buildJob);

        mockServer.verify();
        assertThat(persisted).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_persist_exception() {
        BuildJobData buildJob = new BuildJobData().setName("buildJob");
        setupPostExceptionExpectations(V1ContextPaths.BUILDJOBS, buildJob);
        try {
            cut.persist(buildJob);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findAll() {

        BuildJobData buildJob1 = new BuildJobData().setId(ID);
        BuildJobData buildJob2 = new BuildJobData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(V1ContextPaths.BUILDJOBS, Arrays.asList(buildJob1, buildJob2));

        Set<BuildJobData> found = cut.findAll();

        mockServer.verify();
        assertThat(found).containsOnly(buildJob1, buildJob2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findAll_exception() {
        setupGetExceptionExpectations(V1ContextPaths.BUILDJOBS);
        try {
            cut.findAll();
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        BuildJobData expected = new BuildJobData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.BUILDJOB_FOR_ID, ID), expected);

        BuildJobData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.BUILDJOB_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findBuilds() {

        BuildData build1 = new BuildData().setId(ID);
        BuildData build2 = new BuildData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.BUILDS_OF_BUILDJOB_FOR_ID, ID), Arrays.asList(build1, build2));

        Set<BuildData> found = cut.findBuilds(ID);

        mockServer.verify();
        assertThat(found).containsOnly(build1, build2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findBuilds_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.BUILDS_OF_BUILDJOB_FOR_ID, ID));
        try {
            cut.findBuilds(ID);
        } finally {
            mockServer.verify();
        }
    }

}
