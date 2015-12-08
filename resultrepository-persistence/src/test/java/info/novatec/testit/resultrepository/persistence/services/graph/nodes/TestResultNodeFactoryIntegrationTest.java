package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestResultNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final long UNKNOWN_ID = -1L;

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingTestResultWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TestResultNode> testResult = testResultFactory.getFromGraph(UNKNOWN_ID);
            assertThat(testResult.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTestResultWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            TestResultNode testResult1 = testResultFactory.createInGraph();
            TestResultNode testResult2 = testResultFactory.getFromGraph(testResult1.getId()).get();

            assertThat(testResult2).isEqualTo(testResult1);

        });
    }

}
