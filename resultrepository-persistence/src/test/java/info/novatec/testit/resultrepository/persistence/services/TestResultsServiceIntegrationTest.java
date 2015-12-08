package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.TestResultsService;


public class TestResultsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    TestResultsService cut;

    @Test
    public void testFindTestResultById() {

        TestResultData result1 = createTestResult();
        TestResultData result2 = createTestResult();

        TestResultData foundResult1 = cut.findById(result1.getId());
        TestResultData foundResult2 = cut.findById(result2.getId());

        assertThat(foundResult1).isEqualTo(result1);
        assertThat(foundResult2).isEqualTo(result2);

    }

    @Test
    public void testFindTestForTestResultWithId() {

        TestData test = createTest("test");
        TestResultData result1 = createTestResult();
        TestResultData result2 = createTestResult(test);

        TestData foundTest1 = cut.findTest(result1.getId());
        TestData foundTest2 = cut.findTest(result2.getId());

        assertThat(foundTest1).isNull();
        assertThat(foundTest2).isEqualTo(test);

    }

    @Test
    public void testFindTestGroupResultForTestResultWithId() {

        TestGroupResultData testGroupResult = createTestGroupResult();
        TestResultData result1 = createTestResult();
        TestResultData result2 = createTestResult(testGroupResult);

        TestGroupResultData foundTestGroupResult1 = cut.findTestGroupResult(result1.getId());
        TestGroupResultData foundTestGroupResult2 = cut.findTestGroupResult(result2.getId());

        assertThat(foundTestGroupResult1).isNull();
        assertThat(foundTestGroupResult2.getId()).isEqualTo(testGroupResult.getId());

    }

    @Test
    public void testFindTagsForTestResultWithId() {

        TagData tag1 = createTag("tag1");
        TagData tag2 = createTag("tag2");
        TestResultData result = createTestResult(tag1, tag2);

        Set<TagData> foundTags = cut.findTags(result.getId());

        assertThat(foundTags).containsOnly(tag1, tag2);

    }

    @Test
    public void testFindTagsForTestResultWithIdWithFilter() {

        TagData tag1 = createTag("fooTag");
        TagData tag2 = createTag("barTag");
        TagData tag3 = createTag("fooBarTag");
        TestResultData result = createTestResult(tag1, tag2, tag3);

        Predicate<Tag> filter = tag -> tag.getValue().startsWith("foo");
        Set<TagData> foundTags = cut.findTags(result.getId(), filter);

        assertThat(foundTags).containsOnly(tag1, tag3).doesNotContain(tag2);

    }

    @Test
    public void testFindTestResultDetailsForTestResultWithId() {

        TestResultData result = createTestResult();
        TestResultDetailData detail1 = createTestResultDetail(result);
        TestResultDetailData detail2 = createTestResultDetail(result);

        List<TestResultDetailData> foundDetails = cut.findTestResultDetails(result.getId());

        assertThat(foundDetails).containsOnly(detail1, detail2);

    }

    @Test
    public void testFindTestResultDetailsForTestResultWithIdWithFilter() {

        TestResultData result = createTestResult();
        TestResultDetailData detail1 = createTestResultDetail(result, DetailType.SUCCESS);
        TestResultDetailData detail2 = createTestResultDetail(result, DetailType.ERROR);
        TestResultDetailData detail3 = createTestResultDetail(result, DetailType.SUCCESS);

        Predicate<TestResultDetail> filter = detail -> detail.getType().equals(DetailType.SUCCESS);
        List<TestResultDetailData> foundDetails = cut.findTestResultDetails(result.getId(), filter);

        assertThat(foundDetails).containsOnly(detail1, detail3).doesNotContain(detail2);

    }

}
