package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestResultDetailNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    /* type */

    @Test
    public void testThatNoTypeDefaultsToInformation() {
        inTransaction(() -> {
            TestResultDetailNode testResultDetail = testResultDetailFactory.createInGraph().setType(null);
            assertThat(testResultDetail.getType()).isSameAs(DetailType.INFORMATION);
        });
    }

    @Test
    public void testThatTypeCanBeChanged() {
        inTransaction(() -> {
            TestResultDetailNode testResultDetail = testResultDetailFactory.createInGraph().setType(DetailType.SUCCESS);
            assertThat(testResultDetail.getType()).isSameAs(DetailType.SUCCESS);
        });
    }

    /* message */

    @Test
    public void testThatNoMessageReturnsNull() {
        inTransaction(() -> {
            TestResultDetailNode testResultDetail = testResultDetailFactory.createInGraph();
            assertThat(testResultDetail.getMessage()).isNull();
        });
    }

    @Test
    public void testThatMessageCanBeChanged() {
        inTransaction(() -> {
            TestResultDetailNode testResultDetail = testResultDetailFactory.createInGraph().setMessage("foo bar");
            assertThat(testResultDetail.getMessage()).isEqualTo("foo bar");
        });
    }

    /* test result */

    @Test
    public void testThatNonExistingTestResultReturnEmptyOptional() {
        inTransaction(() -> {
            TestResultDetailNode testResultDetail = testResultDetailFactory.createInGraph();
            Optional<TestResultNode> testResult = testResultDetail.getTestResult();
            assertThat(testResult.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatTestResultCanBeRetrieved() {
        inTransaction(() -> {

            TestResultDetailNode testResultDetail = testResultDetailFactory.createInGraph();
            TestResultNode testResult1 = testResultFactory.createInGraph();
            testResult1.addTestResultDetail(testResultDetail);

            TestResultNode testResult2 = testResultDetail.getTestResult().get();

            assertThat(testResult2).isEqualTo(testResult1);

        });
    }

}
