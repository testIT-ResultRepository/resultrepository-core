package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestGroupResultNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    /* tags */

    @Test
    public void testThatGetTagsReturnsEmptySetIfNoTagsAreLinked() {
        inTransaction(() -> {
            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            assertThat(testGroupResult.getTags()).isEmpty();
        });
    }

    @Test
    public void testThatGetTagsReturnsAllLinkedTags() {
        inTransaction(() -> {

            TagNode tag1 = tagFactory.getOrCreateFromGraph("tag1");
            TagNode tag2 = tagFactory.getOrCreateFromGraph("tag2");
            TagNode tag3 = tagFactory.getOrCreateFromGraph("tag3");

            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            testGroupResult.linkToTags(tag1, tag2);

            assertThat(testGroupResult.getTags()).containsOnly(tag1, tag2).doesNotContain(tag3);

        });
    }

    /* meta data */

    @Test
    public void testThatGetMetadataValuesReturnsEmptySetIfNoMetadataValuesAreLinked() {
        inTransaction(() -> {
            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            assertThat(testGroupResult.getMetadataValues()).isEmpty();
        });
    }

    @Test
    public void testThatGetMetadataValuesReturnsAllLinkedMetadataValues() {
        inTransaction(() -> {

            MetadataValueNode metadataValue1 = metadataValueFactory.getOrCreateFromGraph(new MetadataPath("os", "windows"));
            MetadataValueNode metadataValue2 = metadataValueFactory.getOrCreateFromGraph(new MetadataPath("os", "linux"));
            MetadataValueNode metadataValue3 = metadataValueFactory.getOrCreateFromGraph(new MetadataPath("os", "mac os"));

            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            testGroupResult.linkToMetadataValues(metadataValue1, metadataValue2);

            assertThat(testGroupResult.getMetadataValues()).containsOnly(metadataValue1, metadataValue2)
                .doesNotContain(metadataValue3);

        });
    }

    /* test results */

    @Test
    public void testThatGetTestResultsReturnsAllTestResultsOfTheTestGroupResult() {
        inTransaction(() -> {

            TestResultNode testResult1 = testResultFactory.createInGraph();
            TestResultNode testResult2 = testResultFactory.createInGraph();
            TestResultNode testResult3 = testResultFactory.createInGraph();

            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            testGroupResult.addTestResults(testResult1, testResult2);

            assertThat(testGroupResult.getTestResults()).containsOnly(testResult1, testResult2).doesNotContain(testResult3);

        });
    }

    /* test groups */

    @Test
    public void testThatGettingNonExistingTestGroupReturnsNull() {
        inTransaction(() -> {
            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            assertThat(testGroupResult.getTestGroup()).isNull();
        });
    }

    @Test
    public void testThatGettingExistingTestGroupReturnsIt() {
        inTransaction(() -> {

            TestGroupNode testGroup = testGroupFactory.getOrCreateFromGraph("testGroup");

            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            testGroupResult.setTestGroup(testGroup);

            assertThat(testGroupResult.getTestGroup()).isEqualTo(testGroup);
            assertThat(testGroup.getTestGroupResults()).contains(testGroupResult);

        });
    }

    @Test
    public void testThatChaningTheTestGroupRemovesExistingRelationship() {
        inTransaction(() -> {

            TestGroupNode testGroup1 = testGroupFactory.getOrCreateFromGraph("testGroup1");
            TestGroupNode testGroup2 = testGroupFactory.getOrCreateFromGraph("testGroup2");

            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            testGroupResult.setTestGroup(testGroup1);
            testGroupResult.setTestGroup(testGroup2);

            assertThat(testGroupResult.getTestGroup()).isEqualTo(testGroup2);
            assertThat(testGroup2.getTestGroupResults()).contains(testGroupResult);

            assertThat(testGroup1.getTestGroupResults()).doesNotContain(testGroupResult);

        });
    }

    /* builds */

    @Test
    public void testThatGettingNonExistingBuildReturnsNull() {
        inTransaction(() -> {
            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            assertThat(testGroupResult.getBuild()).isNull();
        });
    }

    @Test
    public void testThatGettingExistingBuildReturnsIt() {
        inTransaction(() -> {

            BuildNode build = buildFactory.getOrCreateFromGraph("build_job", 256);

            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            testGroupResult.setBuild(build);

            assertThat(testGroupResult.getBuild()).isEqualTo(build);
            assertThat(build.getTestGroupResults()).contains(testGroupResult);

        });
    }

    @Test
    public void testThatChaningTheBuildRemovesExistingRelationship() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph("build_job", 256);
            BuildNode build2 = buildFactory.getOrCreateFromGraph("build_job", 512);

            TestGroupResultNode testGroupResult = testGroupResultFactory.createInGraph();
            testGroupResult.setBuild(build1);
            testGroupResult.setBuild(build2);

            assertThat(testGroupResult.getBuild()).isEqualTo(build2);
            assertThat(build2.getTestGroupResults()).contains(testGroupResult);

            assertThat(build1.getTestGroupResults()).doesNotContain(testGroupResult);

        });
    }

}
