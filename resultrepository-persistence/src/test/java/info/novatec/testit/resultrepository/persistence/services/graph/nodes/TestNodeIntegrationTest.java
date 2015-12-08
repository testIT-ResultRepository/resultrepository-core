package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String TEST = "foo.bar.Test#test1";

    /* test results */

    @Test
    public void testThatAllTestResultsCanBeRetrievedAsAStream() {
        inTransaction(() -> {

            TestResultNode testResult1 = testResultFactory.createInGraph();
            TestResultNode testResult2 = testResultFactory.createInGraph();
            TestResultNode testResult3 = testResultFactory.createInGraph();

            TestNode test = testFactory.getOrCreateFromGraph(TEST);
            testResult1.setTest(test);
            testResult2.setTest(test);

            Set<TestResultNode> testResults = test.getTestResults();
            assertThat(testResults).containsOnly(testResult1, testResult2).doesNotContain(testResult3);

        });
    }

    @Test
    public void testThatLatestTestResultCanBeRetrievedAsAnOptional() {
        inTransaction(() -> {

            TestNode test = testFactory.getOrCreateFromGraph(TEST);

            TestResultNode testResult1 = testResultFactory.createInGraph();
            TestResultNode testResult2 = testResultFactory.createInGraph();
            waitToGuaranteeLatestTimestamp();
            TestResultNode testResult3 = testResultFactory.createInGraph();

            testResult1.setTest(test);
            testResult2.setTest(test);
            testResult3.setTest(test);

            assertThat(test.getLatestResult().get()).isEqualTo(testResult3);

        });
    }

    private void waitToGuaranteeLatestTimestamp() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Test
    public void testThatLatestTestResultCanBeRetrievedAsAnOptional_NoLatestResult() {
        inTransaction(() -> {
            TestNode test = testFactory.getOrCreateFromGraph(TEST);
            assertThat(test.getLatestResult().isPresent()).isFalse();
        });
    }

}
