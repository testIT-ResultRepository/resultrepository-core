package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.MetadataKindData;
import info.novatec.testit.resultrepository.api.dto.MetadataValueData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.MetadataValueNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNode;


public class ImportServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    private TagData tag1 = new TagData().setValue("tag 1");
    private TagData tag2 = new TagData().setValue("tag 2");
    private TagData tag3 = new TagData().setValue("tag 3");

    private MetadataKindData operatingSystem = new MetadataKindData().setName("operating system");
    private MetadataKindData browser = new MetadataKindData().setName("browser");
    private MetadataValueData windows = new MetadataValueData().setMetadataKind(operatingSystem).setValue("windows");
    private MetadataValueData fireforx34 = new MetadataValueData().setMetadataKind(browser).setValue("firefox:34");
    private MetadataValueData fireforx35 = new MetadataValueData().setMetadataKind(browser).setValue("firefox:35");

    private BuildJobData buildJob = new BuildJobData().setName("snapshot build");
    private BuildData buildNo1 = new BuildData().setBuildJob(buildJob).setBuildNumber(1);

    private TestGroupData fooTests = new TestGroupData().setName("tests.FooTests");
    private TestGroupData barIntegrationTests = new TestGroupData().setName("tests.BarIntegrationTests");

    private TestData fooTest1 = new TestData().setName("tests.FooTests#test1");
    private TestData barIntegrationTest1 = new TestData().setName("tests.BarIntegrationTests#test1");
    private TestData barIntegrationTest2 = new TestData().setName("tests.BarIntegrationTests#test2");

    @Autowired
    ImportServiceImpl cut;

    @Test
    public void testBasicImportWithoutOtherEntities() {

        TestGroupResultData testGroupResult = new TestGroupResultData();

        long id = importAndGetId(testGroupResult);

        inTransaction(() -> {

            TestGroupResultNode resultFromGraph = testGroupResultFactory.getFromGraph(id).get();

            assertThat(resultFromGraph.getBuild()).isNull();
            assertThat(resultFromGraph.getTestGroup()).isNull();
            assertThat(resultFromGraph.getTags()).isEmpty();
            assertThat(resultFromGraph.getMetadataValues()).isEmpty();
            assertThat(resultFromGraph.getTestResults()).isEmpty();

        });

    }

    @Test
    public void testThatTestGroupResultsAreImportedWithAllRelatedData() {

        Map<String, Object> customProperties = new HashMap<>();
        customProperties.put("key1", 42);
        customProperties.put("key2", "foo");
        customProperties.put("key3", 4.2f);

        TestGroupResultData testGroupResult = new TestGroupResultData();
        testGroupResult.setBuild(buildNo1);
        testGroupResult.setTestGroup(fooTests);
        testGroupResult.addTags(tag1, tag2);
        testGroupResult.addMetadataValues(windows, fireforx34);
        testGroupResult.setCreationTimestamp(1000L);
        testGroupResult.setCustomProperties(customProperties);

        long id = importAndGetId(testGroupResult);

        inTransaction(() -> {

            BuildNode buildNode = buildFactory.getFromGraph(buildJob.getName(), buildNo1.getBuildNumber()).get();
            TestGroupNode testGroupNode = testGroupFactory.getFromGraph(fooTests.getName()).get();
            TagNode tag1Node = tagFactory.getFromGraph(tag1.getValue()).get();
            TagNode tag2Node = tagFactory.getFromGraph(tag2.getValue()).get();
            MetadataValueNode windowsNode = metadataValueFactory.getOrCreateFromGraph(MetadataPath.from(windows));
            MetadataValueNode fireforx34Node = metadataValueFactory.getOrCreateFromGraph(MetadataPath.from(fireforx34));

            TestGroupResultNode resultFromGraph = testGroupResultFactory.getFromGraph(id).get();

            assertThat(resultFromGraph.getBuild()).isEqualTo(buildNode);
            assertThat(resultFromGraph.getTestGroup()).isEqualTo(testGroupNode);
            assertThat(resultFromGraph.getTags()).containsOnly(tag1Node, tag2Node);
            assertThat(resultFromGraph.getMetadataValues()).containsOnly(windowsNode, fireforx34Node);
            assertThat(resultFromGraph.getCustomProperties()).isEqualTo(customProperties);
            assertThat(resultFromGraph.getCreationTimestamp()).isEqualTo(1000L);

        });

    }

    @Test
    public void testThatTestResultsAreImportedWithAllRelatedData() {

        Map<String, Object> customProperties = new HashMap<>();
        customProperties.put("key1", 42);
        customProperties.put("key2", "foo");
        customProperties.put("key3", 4.2f);

        TestResultData testResult = new TestResultData();
        testResult.setTest(fooTest1);
        testResult.addTags(tag2, tag3);
        testResult.addMetadataValues(windows, fireforx35);
        testResult.setCreationTimestamp(1000L);
        testResult.setCustomProperties(customProperties);
        testResult.setDuration(100L);
        testResult.setStatus(ResultStatus.PASSED);

        long id = importAndGetId(testResult);

        inTransaction(() -> {

            TestNode testNode = testFactory.getFromGraph(fooTest1.getName()).get();
            TagNode tag2Node = tagFactory.getFromGraph(tag2.getValue()).get();
            TagNode tag3Node = tagFactory.getFromGraph(tag3.getValue()).get();
            MetadataValueNode windowsNode = metadataValueFactory.getOrCreateFromGraph(MetadataPath.from(windows));
            MetadataValueNode fireforx35Node = metadataValueFactory.getOrCreateFromGraph(MetadataPath.from(fireforx35));

            TestResultNode resultFromGraph = testResultFactory.getFromGraph(id).get();

            assertThat(resultFromGraph.getTest()).isEqualTo(testNode);
            assertThat(resultFromGraph.getTags()).containsOnly(tag2Node, tag3Node);
            assertThat(resultFromGraph.getMetadataValues()).containsOnly(windowsNode, fireforx35Node);
            assertThat(resultFromGraph.getCustomProperties()).isEqualTo(customProperties);
            assertThat(resultFromGraph.getCreationTimestamp()).isEqualTo(1000L);
            assertThat(resultFromGraph.getDuration()).isEqualTo(100L);
            assertThat(resultFromGraph.getStatus()).isEqualTo(ResultStatus.PASSED);

        });

    }

    @Test
    public void testThatTestResultDetailsAreImportedWithAllRelatedData() {

        TestResultDetailData testResultDetail = new TestResultDetailData();
        testResultDetail.setCreationTimestamp(1000L);
        testResultDetail.setMessage("This is a warning message!");
        testResultDetail.setType(DetailType.WARNING);

        long id = importAndGetId(testResultDetail);

        inTransaction(() -> {

            TestResultDetailNode resultFromGraph = testResultDetailFactory.getFromGraph(id).get();

            assertThat(resultFromGraph.getCustomProperties()).isEmpty();
            assertThat(resultFromGraph.getCreationTimestamp()).isEqualTo(1000L);
            assertThat(resultFromGraph.getMessage()).isEqualTo("This is a warning message!");
            assertThat(resultFromGraph.getType()).isEqualTo(DetailType.WARNING);

        });

    }

    @Test
    public void testThatExtendedBuildInformationIsImported() {

        Map<String, Object> customProperties = new HashMap<>();
        customProperties.put("key1", 42);
        customProperties.put("key2", "foo");
        customProperties.put("key3", 4.2f);

        buildNo1.addTags(tag1, tag3);
        buildNo1.setCustomProperties(customProperties);

        importAndGetId(new TestGroupResultData().setBuild(buildNo1));

        inTransaction(() -> {

            TagNode tag1Node = tagFactory.getFromGraph(tag1.getValue()).get();
            TagNode tag3Node = tagFactory.getFromGraph(tag3.getValue()).get();

            BuildNode buildNode = buildFactory.getFromGraph(buildJob.getName(), buildNo1.getBuildNumber()).get();

            assertThat(buildNode.getTags()).containsOnly(tag1Node, tag3Node);
            assertThat(buildNode.getCustomProperties()).isEqualTo(customProperties);

        });

    }

    @Test
    public void testThatResultTreeIsImportedCompletely() {

        TestGroupResultData testGroupResult = new TestGroupResultData().setTestGroup(barIntegrationTests);

        TestResultData testResultData1 = new TestResultData().setTest(barIntegrationTest1);
        TestResultData testResultData2 = new TestResultData().setTest(barIntegrationTest2);

        TestResultDetailData testResultData11 = new TestResultDetailData().setMessage("first");
        TestResultDetailData testResultData12 = new TestResultDetailData().setMessage("second");
        TestResultDetailData testResultData21 = new TestResultDetailData().setMessage("third");
        TestResultDetailData testResultData22 = new TestResultDetailData().setMessage("fourth");

        testGroupResult.addTestResults(testResultData1, testResultData2);
        testResultData1.addTestResultDetails(testResultData11, testResultData12);
        testResultData2.addTestResultDetails(testResultData21, testResultData22);

        long id = importAndGetId(testGroupResult);

        inTransaction(() -> {

            TestGroupResultNode resultFromGraph = testGroupResultFactory.getFromGraph(id).get();
            assertThat(resultFromGraph.getTestGroup().getName()).isEqualTo(barIntegrationTests.getName());

            Set<TestResultNode> testResults = resultFromGraph.getTestResults();
            assertThat(testResults).hasSize(2);

            Iterator<TestResultNode> testResultsIterator = testResults.iterator();
            TestResultNode testResult1 = testResultsIterator.next();
            TestResultNode testResult2 = testResultsIterator.next();

            assertThat(testResult1.getTestResultDetails()).hasSize(2);
            assertThat(testResult2.getTestResultDetails()).hasSize(2);

        });

    }

    /* utilities */

    private Long importAndGetId(TestGroupResultData testGroupResult) {
        return inTransaction(() -> cut.importTestGroupResult(testGroupResult).getId());
    }

    private Long importAndGetId(TestResultData testResult) {
        return inTransaction(() -> cut.importTestResult(testResult).getId());
    }

    private Long importAndGetId(TestResultDetailData testResultDetail) {
        return inTransaction(() -> cut.importTestResultDetail(testResultDetail).getId());
    }

}
