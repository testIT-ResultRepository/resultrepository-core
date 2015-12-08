package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.TagsService;


public class TagsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    TagsService cut;

    @Test
    public void testPersistenceOfNewTag() {

        TagData tag = new TagData().setValue("tag");

        TagData persistedTag = cut.persist(tag);

        assertThat(persistedTag.getId()).isGreaterThanOrEqualTo(0);
        assertThat(persistedTag.getValue()).isEqualTo("tag");

    }

    @Test
    public void testThatMultiplePersistenceOfSameTagWillOnlyCreateOneTagInRepository() {

        TagData tag = new TagData().setValue("tag");

        TagData persistedTag1 = cut.persist(tag);
        TagData persistedTag2 = cut.persist(tag);

        assertThat(persistedTag1).isEqualTo(persistedTag2);
        assertThat(persistedTag1).isNotSameAs(persistedTag2);

    }

    @Test
    public void testFindAllTags() {

        TagData tag1 = createTag("tag1");
        TagData tag2 = createTag("tag2");

        Set<TagData> tests = cut.findAll();

        assertThat(tests).containsOnly(tag1, tag2);

    }

    @Test
    public void testFindAllTagsWithFilter() {

        TagData tag1 = createTag("fooTag");
        TagData tag2 = createTag("barTag");
        TagData test3 = createTag("fooBarTag");

        Set<TagData> tags = cut.findAll(tag -> tag.getValue().startsWith("foo"));

        assertThat(tags).containsOnly(tag1, test3).doesNotContain(tag2);

    }

    @Test
    public void testFindTagById() {

        TagData tag1 = createTag("tag1");
        TagData tag2 = createTag("tag2");

        TagData foundTag1 = cut.findById(tag1.getId());
        TagData foundTag2 = cut.findById(tag2.getId());

        assertThat(foundTag1).isEqualTo(tag1);
        assertThat(foundTag2).isEqualTo(tag2);

    }

    @Test
    public void testFindTagByValue() {

        TagData tag1 = createTag("tag1");
        TagData tag2 = createTag("tag2");

        TagData foundTag1 = cut.findByValue(tag1.getValue());
        TagData foundTag2 = cut.findByValue(tag2.getValue());

        assertThat(foundTag1).isEqualTo(tag1);
        assertThat(foundTag2).isEqualTo(tag2);

    }

    @Test
    public void testFindBuildsForTagWithId() {

        TagData tag = createTag("tag");

        BuildData build1 = createBuild("build", 1, tag);
        BuildData build2 = createBuild("build", 2, tag);
        BuildData build3 = createBuild("build", 3, tag);

        Set<BuildData> foundBuilds = cut.findBuilds(tag.getId());

        assertThat(foundBuilds).containsOnly(build1, build2, build3);

    }

    @Test
    public void testFindBuildsForTagWithIdWithFilter() {

        TagData tag = createTag("tag");
        BuildData build1 = createBuild("fooBuild", 1, tag);
        BuildData build2 = createBuild("barBuild", 1, tag);
        BuildData build3 = createBuild("fooBarBuild", 1, tag);

        Predicate<Build> filter = build -> build.getBuildJob().getName().startsWith("foo");
        Set<BuildData> foundBuilds = cut.findBuilds(tag.getId(), filter);

        assertThat(foundBuilds).containsOnly(build1, build3).doesNotContain(build2);

    }

    @Test
    public void testFindTestGroupResultsForTagWithId() {

        TagData tag = createTag("tag");
        TestGroupResultData result1 = createTestGroupResult(tag);
        TestGroupResultData result2 = createTestGroupResult(tag);
        TestGroupResultData result3 = createTestGroupResult(tag);

        Set<TestGroupResultData> foundResults = cut.findTestGroupResults(tag.getId());

        assertThat(foundResults).containsOnly(result1, result2, result3);

    }

    @Test
    public void testFindTestGroupResultsForTagWithIdWithFilter() {

        TagData tag = createTag("tag");
        TestGroupResultData result1 = createTestGroupResult(tag);
        TestGroupResultData result2 = createTestGroupResult(tag);
        TestGroupResultData result3 = createTestGroupResult(tag);

        Set<Long> ids = Sets.newSet(result1.getId(), result3.getId());
        Predicate<TestGroupResult> filter = result -> ids.contains(result.getId());
        Set<TestGroupResultData> foundResults = cut.findTestGroupResults(tag.getId(), filter);

        assertThat(foundResults).containsOnly(result1, result3).doesNotContain(result2);

    }

    @Test
    public void testFindTestResultsForTagWithId() {

        TagData tag = createTag("tag");
        TestResultData result1 = createTestResult(tag);
        TestResultData result2 = createTestResult(tag);
        TestResultData result3 = createTestResult(tag);

        Set<TestResultData> foundResults = cut.findTestResults(tag.getId());

        assertThat(foundResults).containsOnly(result1, result2, result3);

    }

    @Test
    public void testFindTestResultsForTagWithIdWithFilter() {

        TagData tag = createTag("tag");
        TestResultData result1 = createTestResult(tag);
        TestResultData result2 = createTestResult(tag);
        TestResultData result3 = createTestResult(tag);

        Set<Long> ids = Sets.newSet(result1.getId(), result3.getId());
        Predicate<TestResult> filter = result -> ids.contains(result.getId());
        Set<TestResultData> foundResults = cut.findTestResults(tag.getId(), filter);

        assertThat(foundResults).containsOnly(result1, result3).doesNotContain(result2);

    }

}
