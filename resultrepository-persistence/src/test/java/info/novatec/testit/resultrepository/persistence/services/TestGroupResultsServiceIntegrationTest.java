package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.TestGroupResultsService;


public class TestGroupResultsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    TestGroupResultsService cut;

    @Test
    public void testFindTestGroupResultById() {

        TestGroupResultData result1 = createTestGroupResult();
        TestGroupResultData result2 = createTestGroupResult();

        TestGroupResultData foundResult1 = cut.findById(result1.getId());
        TestGroupResultData foundResult2 = cut.findById(result2.getId());

        assertThat(foundResult1).isEqualTo(result1);
        assertThat(foundResult2).isEqualTo(result2);

    }

    @Test
    public void testFindTestGroupForTestGroupResultWithId() {

        TestGroupData testGroup = createTestGroup("testGroup");
        TestGroupResultData result1 = createTestGroupResult();
        TestGroupResultData result2 = createTestGroupResult(testGroup);

        TestGroupData foundTestGroup1 = cut.findTestGroup(result1.getId());
        TestGroupData foundTestGroup2 = cut.findTestGroup(result2.getId());

        assertThat(foundTestGroup1).isNull();
        assertThat(foundTestGroup2).isEqualTo(testGroup);

    }

    @Test
    public void testFindBuildForTestGroupResultWithId() {

        BuildData build = createBuild("build", 1);
        TestGroupResultData result1 = createTestGroupResult();
        TestGroupResultData result2 = createTestGroupResult(build);

        BuildData foundBuild1 = cut.findBuild(result1.getId());
        BuildData foundBuild2 = cut.findBuild(result2.getId());

        assertThat(foundBuild1).isNull();
        assertThat(foundBuild2).isEqualTo(build);

    }

    @Test
    public void testFindTagsForTestGroupResultWithId() {

        TagData tag1 = createTag("tag1");
        TagData tag2 = createTag("tag2");
        TestGroupResultData result = createTestGroupResult(tag1, tag2);

        Set<TagData> foundTags = cut.findTags(result.getId());

        assertThat(foundTags).containsOnly(tag1, tag2);

    }

    @Test
    public void testFindTagsForTestGroupResultWithIdWithFilter() {

        TagData tag1 = createTag("fooTag");
        TagData tag2 = createTag("barTag");
        TagData tag3 = createTag("fooBarTag");
        TestGroupResultData result = createTestGroupResult(tag1, tag2, tag3);

        Predicate<Tag> filter = tag -> tag.getValue().startsWith("foo");
        Set<TagData> foundTags = cut.findTags(result.getId(), filter);

        assertThat(foundTags).containsOnly(tag1, tag3).doesNotContain(tag2);

    }

    @Test
    public void testFindTestResultsForTestGroupResultWithId() {

        TestGroupResultData testGroupResult = createTestGroupResult();
        TestResultData testResult1 = createTestResult(testGroupResult);
        TestResultData testResult2 = createTestResult(testGroupResult);

        Set<TestResultData> foundResults = cut.findTestResults(testGroupResult.getId());

        assertThat(foundResults).containsOnly(testResult1, testResult2);

    }

    @Test
    public void testFindTestGroupResultDetailsForTestGroupResultWithIdWithFilter() {

        TestGroupResultData testGroupResult = createTestGroupResult();
        TestResultData testResult1 = createTestResult(testGroupResult, ResultStatus.PASSED);
        TestResultData testResult2 = createTestResult(testGroupResult, ResultStatus.FAILED);
        TestResultData testResult3 = createTestResult(testGroupResult, ResultStatus.PASSED);

        Predicate<TestResult> filter = testResult -> testResult.getStatus().equals(ResultStatus.PASSED);
        Set<TestResultData> foundResults = cut.findTestResults(testGroupResult.getId(), filter);

        assertThat(foundResults).containsOnly(testResult1, testResult3).doesNotContain(testResult2);

    }

}
