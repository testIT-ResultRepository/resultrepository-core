package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestResultNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    /* duration */

    @Test
    public void testThatNegativeDurationsWillBeNormalizedToZero() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph().setDuration(-1L);
            assertThat(testResult.getDuration()).isZero();
        });
    }

    @Test
    public void testThatNormalDurationsCanBeSet() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph().setDuration(5124L);
            assertThat(testResult.getDuration()).isEqualTo(5124L);
        });
    }

    @Test
    public void testThatVeryLongDurationsCanBeSet() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph().setDuration(Long.MAX_VALUE);
            assertThat(testResult.getDuration()).isEqualTo(Long.MAX_VALUE);
        });
    }

    /* result status */

    @Test
    public void testThatStatusDefaultsToUnknown() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph().setStatus(null);
            assertThat(testResult.getStatus()).isEqualTo(ResultStatus.UNKNOWN);
        });
    }

    @Test
    public void testThatStatusCanBeChanged() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph().setStatus(ResultStatus.PASSED);
            assertThat(testResult.getStatus()).isEqualTo(ResultStatus.PASSED);
        });
    }

    /* tags */

    @Test
    public void testThatGetTagsReturnsEmptySetIfNoTagsAreLinked() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph();
            assertThat(testResult.getTags()).isEmpty();
        });
    }

    @Test
    public void testThatGetTagsReturnsAllLinkedTags() {
        inTransaction(() -> {

            TagNode tag1 = tagFactory.getOrCreateFromGraph("tag1");
            TagNode tag2 = tagFactory.getOrCreateFromGraph("tag2");
            TagNode tag3 = tagFactory.getOrCreateFromGraph("tag3");

            TestResultNode testResult = testResultFactory.createInGraph();
            testResult.linkToTags(tag1, tag2);

            assertThat(testResult.getTags()).containsOnly(tag1, tag2).doesNotContain(tag3);

        });
    }

    /* meta data */

    @Test
    public void testThatGetMetadataValuesReturnsEmptySetIfNoMetadataValuesAreLinked() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph();
            assertThat(testResult.getMetadataValues()).isEmpty();
        });
    }

    @Test
    public void testThatGetMetadataValuesReturnsAllLinkedMetadataValues() {
        inTransaction(() -> {

            MetadataValueNode metadataValue1 = metadataValueFactory.getOrCreateFromGraph(new MetadataPath("os", "windows"));
            MetadataValueNode metadataValue2 = metadataValueFactory.getOrCreateFromGraph(new MetadataPath("os", "linux"));
            MetadataValueNode metadataValue3 = metadataValueFactory.getOrCreateFromGraph(new MetadataPath("os", "mac os"));

            TestResultNode testResult = testResultFactory.createInGraph();
            testResult.linkToMetadataValues(metadataValue1, metadataValue2);

            assertThat(testResult.getMetadataValues()).containsOnly(metadataValue1, metadataValue2)
                .doesNotContain(metadataValue3);

        });
    }

    /* test result details */

    @Test
    public void testThatGetTestResultDetailsReturnsAllTestResultDetailsOfTheTestResult() {
        inTransaction(() -> {

            TestResultDetailNode testResultDetail1 = testResultDetailFactory.createInGraph();
            TestResultDetailNode testResultDetail2 = testResultDetailFactory.createInGraph();
            TestResultDetailNode testResultDetail3 = testResultDetailFactory.createInGraph();
            TestResultDetailNode testResultDetail4 = testResultDetailFactory.createInGraph();

            testResultDetail1.setCreationTimestamp(300);
            testResultDetail2.setCreationTimestamp(100);
            testResultDetail3.setCreationTimestamp(200);
            testResultDetail4.setCreationTimestamp(400);

            TestResultNode testResult = testResultFactory.createInGraph();
            testResult.addTestResultDetails(testResultDetail1, testResultDetail2, testResultDetail3);

            assertThat(testResult.getTestResultDetails()).containsExactly(testResultDetail2, testResultDetail3,
                testResultDetail1).doesNotContain(testResultDetail4);

        });
    }

    /* tests */

    @Test
    public void testThatGettingNonExistingTestReturnsNull() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph();
            assertThat(testResult.getTest()).isNull();
        });
    }

    @Test
    public void testThatGettingExistingTestReturnsIt() {
        inTransaction(() -> {

            TestNode test = testFactory.getOrCreateFromGraph("test");

            TestResultNode testResult = testResultFactory.createInGraph();
            testResult.setTest(test);

            assertThat(testResult.getTest()).isEqualTo(test);
            assertThat(test.getTestResults()).contains(testResult);

        });
    }

    @Test
    public void testThatChaningTheTestRemovesExistingRelationship() {
        inTransaction(() -> {

            TestNode test1 = testFactory.getOrCreateFromGraph("test1");
            TestNode test2 = testFactory.getOrCreateFromGraph("test2");

            TestResultNode testResult = testResultFactory.createInGraph();
            testResult.setTest(test1);
            testResult.setTest(test2);

            assertThat(testResult.getTest()).isEqualTo(test2);
            assertThat(test2.getTestResults()).contains(testResult);

            assertThat(test1.getTestResults()).doesNotContain(testResult);

        });
    }

    /* test result */

    @Test
    public void testThatNonExistingTestGroupResultReturnEmptyOptional() {
        inTransaction(() -> {
            TestResultNode testResult = testResultFactory.createInGraph();
            Optional<TestGroupResultNode> testGroupResult = testResult.getTestGroupResult();
            assertThat(testGroupResult.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatTestGroupResultCanBeRetrieved() {
        inTransaction(() -> {

            TestResultNode testResult = testResultFactory.createInGraph();
            TestGroupResultNode testGroupResult1 = testGroupResultFactory.createInGraph();
            testGroupResult1.addTestResult(testResult);

            TestGroupResultNode testGroupResult2 = testResult.getTestGroupResult().get();

            assertThat(testGroupResult2).isEqualTo(testGroupResult1);

        });
    }

}
