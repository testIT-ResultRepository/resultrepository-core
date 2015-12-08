package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestGroupResultNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final long UNKNOWN_ID = -1L;

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingTestGroupResultWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TestGroupResultNode> testGroupResult = testGroupResultFactory.getFromGraph(UNKNOWN_ID);
            assertThat(testGroupResult.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTestGroupResultWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            TestGroupResultNode testGroupResult1 = testGroupResultFactory.createInGraph();
            TestGroupResultNode testGroupResult2 = testGroupResultFactory.getFromGraph(testGroupResult1.getId()).get();

            assertThat(testGroupResult2).isEqualTo(testGroupResult1);

        });
    }

}
