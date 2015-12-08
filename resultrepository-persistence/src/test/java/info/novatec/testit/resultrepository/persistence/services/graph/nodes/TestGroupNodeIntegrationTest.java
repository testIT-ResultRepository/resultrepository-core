package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestGroupNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String TEST_GROUP = "foo.bar.Test1";

    /* test group results */

    @Test
    public void testThatAllTestGroupResultsCanBeRetrievedAsAStream() {
        inTransaction(() -> {

            TestGroupResultNode testGroupResult1 = testGroupResultFactory.createInGraph();
            TestGroupResultNode testGroupResult2 = testGroupResultFactory.createInGraph();
            TestGroupResultNode testGroupResult3 = testGroupResultFactory.createInGraph();

            TestGroupNode testGroup = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            testGroupResult1.setTestGroup(testGroup);
            testGroupResult2.setTestGroup(testGroup);

            Set<TestGroupResultNode> testGroupResults = testGroup.getTestGroupResults();
            assertThat(testGroupResults).containsOnly(testGroupResult1, testGroupResult2).doesNotContain(testGroupResult3);

        });
    }

    @Test
    public void testThatLatestTestGroupResultCanBeRetrievedAsAnOptional() {
        inTransaction(() -> {

            TestGroupNode testGroup = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);

            TestGroupResultNode testGroupResult1 = testGroupResultFactory.createInGraph();
            TestGroupResultNode testGroupResult2 = testGroupResultFactory.createInGraph();
            waitToGuaranteeLatestTimestamp();
            TestGroupResultNode testGroupResult3 = testGroupResultFactory.createInGraph();

            testGroupResult1.setTestGroup(testGroup);
            testGroupResult2.setTestGroup(testGroup);
            testGroupResult3.setTestGroup(testGroup);

            assertThat(testGroup.getLatestResult().get()).isEqualTo(testGroupResult3);

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
    public void testThatLatestTestGroupResultCanBeRetrievedAsAnOptional_NoLatestResult() {
        inTransaction(() -> {
            TestGroupNode testGroup = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            assertThat(testGroup.getLatestResult().isPresent()).isFalse();
        });
    }

}
