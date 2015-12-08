package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.BuildJobsService;


public class BuildJobsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    BuildJobsService cut;

    @Test
    public void testPersistenceOfNewBuildJob() {

        BuildJobData buildJob = new BuildJobData().setName("buildJob");

        BuildJobData persistedBuildJob = cut.persist(buildJob);

        assertThat(persistedBuildJob.getId()).isGreaterThanOrEqualTo(0);
        assertThat(persistedBuildJob.getName()).isEqualTo("buildJob");

    }

    @Test
    public void testThatMultiplePersistenceOfSameBuildJobWillOnlyCreateOneBuildJobInRepository() {

        BuildJobData buildJob = new BuildJobData().setName("buildJob");

        BuildJobData persistedBuildJoB1 = cut.persist(buildJob);
        BuildJobData persistedBuildJob2 = cut.persist(buildJob);

        assertThat(persistedBuildJoB1).isEqualTo(persistedBuildJob2);
        assertThat(persistedBuildJoB1).isNotSameAs(persistedBuildJob2);

    }

    @Test
    public void testFindAllBuildJobs() {

        BuildJobData buildJob1 = createBuildJob("buildJob1");
        BuildJobData buildJob2 = createBuildJob("buildJob2");

        Set<BuildJobData> buildJobs = cut.findAll();

        assertThat(buildJobs).containsOnly(buildJob1, buildJob2);

    }

    @Test
    public void testFindAllBuildJobsWithFilter() {

        BuildJobData buildJob1 = createBuildJob("fooBuildJob");
        BuildJobData buildJob2 = createBuildJob("barBuildJob");
        BuildJobData buildJob3 = createBuildJob("fooBarBuildJob");

        Set<BuildJobData> buildJobs = cut.findAll(buildJob -> buildJob.getName().startsWith("foo"));

        assertThat(buildJobs).containsOnly(buildJob1, buildJob3).doesNotContain(buildJob2);

    }

    @Test
    public void testFindBuildJobById() {

        BuildJobData buildJob1 = createBuildJob("buildJob1");
        BuildJobData buildJob2 = createBuildJob("buildJob2");

        BuildJobData foundBuildJob1 = cut.findById(buildJob1.getId());
        BuildJobData foundBuildJob2 = cut.findById(buildJob2.getId());

        assertThat(foundBuildJob1).isEqualTo(buildJob1);
        assertThat(foundBuildJob2).isEqualTo(buildJob2);

    }

    @Test
    public void testFindBuildsForBuildJobWithId() {

        BuildJobData buildJob = createBuildJob("buildJob");

        BuildData build1 = createBuild("buildJob", 1);
        BuildData build2 = createBuild("buildJob", 2);
        BuildData build3 = createBuild("buildJob", 3);

        Set<BuildData> foundBuilds = cut.findBuilds(buildJob.getId());

        assertThat(foundBuilds).containsOnly(build1, build2, build3);

    }

    @Test
    public void testFindBuildsForBuildJobWithIdWithFilter() {

        BuildJobData buildJob = createBuildJob("buildJob");
        BuildData build1 = createBuild("buildJob", 2);
        BuildData build2 = createBuild("buildJob", 3);
        BuildData build3 = createBuild("buildJob", 4);

        Predicate<Build> filter = build -> build.getBuildNumber() % 2 == 0;
        Set<BuildData> foundBuilds = cut.findBuilds(buildJob.getId(), filter);

        assertThat(foundBuilds).containsOnly(build1, build3).doesNotContain(build2);

    }

}
