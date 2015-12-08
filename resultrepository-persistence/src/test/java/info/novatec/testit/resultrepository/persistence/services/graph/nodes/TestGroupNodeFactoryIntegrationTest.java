package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TestGroupNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String TEST_GROUP = "foo.bar.Test1";
    private static final String ANOTHER_TEST_GROUP = "foo.bar.Test2";
    private static final String UNKNOWN_TEST_GROUP = "unknown";

    private static final long UNKNOWN_ID = -1L;

    /* get or create */

    @Test
    public void testGetOrCreateFromGraph_CreatesCorrectNodeOnFirstExecution() {
        inTransaction(() -> {
            TestGroupNode testGroup = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            assertThat(testGroup.getName()).isEqualTo(TEST_GROUP);
        });
    }

    @Test
    public void testGetOrCreateFromGraph_SecondExecutionReturnsSameNodeInNewWrapper() {
        inTransaction(() -> {

            TestGroupNode testGroup1 = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            TestGroupNode testGroup2 = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);

            assertThat(testGroup2).isEqualTo(testGroup1);

        });
    }

    @Test
    public void testGetOrCreateFromGraph_DifferenDataReturnDifferentNodes() {
        inTransaction(() -> {

            TestGroupNode testGroup1 = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            TestGroupNode testGroup2 = testGroupFactory.getOrCreateFromGraph(ANOTHER_TEST_GROUP);

            assertThat(testGroup2).isNotEqualTo(testGroup1);

        });
    }

    /* lookup for name */

    @Test
    public void testThatLookingUpNonExistingTestGroupWithNameReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TestGroupNode> testGroup = testGroupFactory.getFromGraph(UNKNOWN_TEST_GROUP);
            assertThat(testGroup.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTestGroupWithNameReturnsItAsAnOptional() {
        inTransaction(() -> {

            TestGroupNode testGroup1 = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            TestGroupNode testGroup2 = testGroupFactory.getFromGraph(TEST_GROUP).get();

            assertThat(testGroup2).isEqualTo(testGroup1);

        });
    }

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingTestGroupWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TestGroupNode> testGroup = testGroupFactory.getFromGraph(UNKNOWN_ID);
            assertThat(testGroup.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTestGroupWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            TestGroupNode testGroup1 = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            TestGroupNode testGroup2 = testGroupFactory.getFromGraph(testGroup1.getId()).get();

            assertThat(testGroup2).isEqualTo(testGroup1);

        });
    }

    /* get all */

    @Test
    public void testThatGettingAllTestGroupsFromEmptyGraphReturnsEmptyStream() {
        inTransaction(() -> {
            assertEmpty(testGroupFactory.getAllFromGraph());
        });
    }

    @Test
    public void testThatGettingAllTestGroupsFromGraphReturnsThemAllAsAStream() {
        inTransaction(() -> {

            TestGroupNode testGroup1 = testGroupFactory.getOrCreateFromGraph(TEST_GROUP);
            TestGroupNode testGroup2 = testGroupFactory.getOrCreateFromGraph(ANOTHER_TEST_GROUP);

            Stream<TestGroupNode> testGroups = testGroupFactory.getAllFromGraph();

            assertThat(testGroups.collect(Collectors.toSet())).containsOnly(testGroup1, testGroup2);

        });
    }

}
