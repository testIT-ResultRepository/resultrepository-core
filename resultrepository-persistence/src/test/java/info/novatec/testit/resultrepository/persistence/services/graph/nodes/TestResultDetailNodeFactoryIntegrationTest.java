package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestResultDetailNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final long UNKNOWN_ID = -1L;

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingTestResultDetailResultWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TestResultDetailNode> testResultDetail = testResultDetailFactory.getFromGraph(UNKNOWN_ID);
            assertThat(testResultDetail.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTestResultDetailWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            TestResultDetailNode testResultDetail1 = testResultDetailFactory.createInGraph();
            TestResultDetailNode testResultDetail2 = testResultDetailFactory.getFromGraph(testResultDetail1.getId()).get();

            assertThat(testResultDetail2).isEqualTo(testResultDetail1);

        });
    }

}
