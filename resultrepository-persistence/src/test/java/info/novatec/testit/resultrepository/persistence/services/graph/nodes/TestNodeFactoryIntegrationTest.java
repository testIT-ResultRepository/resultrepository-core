package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String TEST = "foo.bar.Test#test1";
    private static final String ANOTHER_TEST = "foo.bar.Test#test2";
    private static final String UNKNOWN_TEST = "unknown";

    private static final long UNKNOWN_ID = -1L;

    /* get or create */

    @Test
    public void testGetOrCreateFromGraph_CreatesCorrectNodeOnFirstExecution() {
        inTransaction(() -> {
            TestNode test = testFactory.getOrCreateFromGraph(TEST);
            assertThat(test.getName()).isEqualTo(TEST);
        });
    }

    @Test
    public void testGetOrCreateFromGraph_SecondExecutionReturnsSameNodeInNewWrapper() {
        inTransaction(() -> {

            TestNode test1 = testFactory.getOrCreateFromGraph(TEST);
            TestNode test2 = testFactory.getOrCreateFromGraph(TEST);

            assertThat(test2).isEqualTo(test1);

        });
    }

    @Test
    public void testGetOrCreateFromGraph_DifferenDataReturnDifferentNodes() {
        inTransaction(() -> {

            TestNode test1 = testFactory.getOrCreateFromGraph(TEST);
            TestNode test2 = testFactory.getOrCreateFromGraph(ANOTHER_TEST);

            assertThat(test2).isNotEqualTo(test1);

        });
    }

    /* lookup for name */

    @Test
    public void testThatLookingUpNonExistingTestWithNameReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TestNode> test = testFactory.getFromGraph(UNKNOWN_TEST);
            assertThat(test.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTestWithNameReturnsItAsAnOptional() {
        inTransaction(() -> {

            TestNode test1 = testFactory.getOrCreateFromGraph(TEST);
            TestNode test2 = testFactory.getFromGraph(TEST).get();

            assertThat(test2).isEqualTo(test1);

        });
    }

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingTestWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TestNode> test = testFactory.getFromGraph(UNKNOWN_ID);
            assertThat(test.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTestWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            TestNode test1 = testFactory.getOrCreateFromGraph(TEST);
            TestNode test2 = testFactory.getFromGraph(test1.getId()).get();

            assertThat(test2).isEqualTo(test1);

        });
    }

    /* get all */

    @Test
    public void testThatGettingAllTestsFromEmptyGraphReturnsEmptyStream() {
        inTransaction(() -> {
            assertEmpty(testFactory.getAllFromGraph());
        });
    }

    @Test
    public void testThatGettingAllTestsFromGraphReturnsThemAllAsAStream() {
        inTransaction(() -> {

            TestNode test1 = testFactory.getOrCreateFromGraph(TEST);
            TestNode test2 = testFactory.getOrCreateFromGraph(ANOTHER_TEST);

            Stream<TestNode> tests = testFactory.getAllFromGraph();

            assertThat(tests.collect(Collectors.toSet())).containsOnly(test1, test2);

        });
    }

}
