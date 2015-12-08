package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.BuildsService;
import info.novatec.testit.resultrepository.server.api.TagsService;


public class BuildsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    TagsService tagsService;

    @Autowired
    BuildsService cut;

    @Test
    public void testPersistenceOfNewBuild() {

        TagData tag1 = tagsService.persist(new TagData().setValue("tag1"));
        TagData tag2 = tagsService.persist(new TagData().setValue("tag2"));

        BuildJobData buildJob = new BuildJobData().setName("buildJob");
        BuildData build = new BuildData().setBuildJob(buildJob)
            .setBuildNumber(1)
            .setCreationTimestamp(123L)
            .addTags(tag1, tag2)
            .putCustomProperty("key1", 1)
            .putCustomProperty("key2", "value");

        BuildData persistedBuild = cut.persist(build);

        assertThat(persistedBuild.getId()).isGreaterThanOrEqualTo(0);
        assertThat(persistedBuild.getBuildJob().getName()).isEqualTo("buildJob");
        assertThat(persistedBuild.getBuildNumber()).isEqualTo(1);
        assertThat(persistedBuild.getCreationTimestamp()).isEqualTo(123L);
        assertThat(persistedBuild.getTags()).containsOnly(tag1, tag2);
        assertThat(persistedBuild.getCustomProperties()).containsEntry("key1", 1).containsEntry("key2", "value").hasSize(2);

    }

    @Test
    public void testThatPersistenceOfNewBuildGeneratesTimestampIfNoneIsGiven() {

        BuildJobData buildJob = new BuildJobData().setName("buildJob");
        BuildData build = new BuildData().setBuildJob(buildJob).setBuildNumber(1);

        BuildData persistedBuild = cut.persist(build);

        assertThat(persistedBuild.getCreationTimestamp()).isGreaterThan(0);

    }

    @Test
    public void testThatMultiplePersistenceOfSameBuildWillOnlyCreateOneBuildInRepository() {

        BuildJobData buildJob = new BuildJobData().setName("buildJob");
        BuildData build = new BuildData().setBuildJob(buildJob).setBuildNumber(1);

        BuildData persistedBuild1 = cut.persist(build);
        BuildData persistedBuild2 = cut.persist(build);

        assertThat(persistedBuild1).isEqualTo(persistedBuild2);
        assertThat(persistedBuild1).isNotSameAs(persistedBuild2);

    }

    @Test
    public void testFindBuildById() {

        BuildData build1 = createBuild("build", 1);
        BuildData build2 = createBuild("build", 2);

        BuildData foundBuild1 = cut.findById(build1.getId());
        BuildData foundBuild2 = cut.findById(build2.getId());

        assertThat(foundBuild1).isEqualTo(build1);
        assertThat(foundBuild2).isEqualTo(build2);

    }

    @Test
    public void testFindBuildJobForBuildWithId() {

        BuildJobData buildJob = createBuildJob("buildJob");
        BuildData build = createBuild("buildJob", 1);

        BuildJobData foundBuildJob = cut.findBuildJob(build.getId());

        assertThat(foundBuildJob).isEqualTo(buildJob);

    }

    @Test
    public void testFindTagsForBuildWithId() {

        TagData tag1 = createTag("tag1");
        TagData tag2 = createTag("tag2");
        BuildData build = createBuild("build", 1, tag1, tag2);

        Set<TagData> foundTags = cut.findTags(build.getId());

        assertThat(foundTags).containsOnly(tag1, tag2);

    }

    @Test
    public void testFindTagsForBuildWithIdWithFilter() {

        TagData tag1 = createTag("fooTag");
        TagData tag2 = createTag("barTag");
        TagData tag3 = createTag("fooBarTag");
        BuildData build = createBuild("build", 1, tag1, tag2, tag3);

        Predicate<Tag> filter = tag -> tag.getValue().startsWith("foo");
        Set<TagData> foundTags = cut.findTags(build.getId(), filter);

        assertThat(foundTags).containsOnly(tag1, tag3).doesNotContain(tag2);

    }

    @Test
    public void testFindTestGroupResultsForBuildWithId() {

        BuildData build = createBuild("build", 1);
        TestGroupResultData result1 = createTestGroupResult(build);
        TestGroupResultData result2 = createTestGroupResult(build);
        TestGroupResultData result3 = createTestGroupResult(build);

        Set<TestGroupResultData> foundResults = cut.findTestGroupResults(build.getId());

        assertThat(foundResults).containsOnly(result1, result2, result3);

    }

    @Test
    public void testFindTestGroupResultsForBuildWithIdWithFilter() {

        BuildData build = createBuild("build", 1);
        TestGroupResultData result1 = createTestGroupResult(build);
        TestGroupResultData result2 = createTestGroupResult(build);
        TestGroupResultData result3 = createTestGroupResult(build);

        Set<Long> ids = Sets.newSet(result1.getId(), result3.getId());
        Predicate<TestGroupResult> filter = result -> ids.contains(result.getId());
        Set<TestGroupResultData> foundResults = cut.findTestGroupResults(build.getId(), filter);

        assertThat(foundResults).containsOnly(result1, result3).doesNotContain(result2);

    }

}
