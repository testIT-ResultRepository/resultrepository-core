package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class BuildNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String BUILD_NAME = "buildName";
    private static final String UNKNOWN_BUILD_NAME = "unknownBuildName";

    private static final int BUILD_NUMBER = 1;
    private static final int ANOTHER_BUILD_NUMBER = 2;
    private static final int UNKNOWN_BUILD_NUMBER = -1;

    private static final long UNKNOWN_ID = -1L;

    /* get or create */

    @Test
    public void testGetOrCreateFromGraph_CreatesCorrectNodesOnFirstExecution() {
        inTransaction(() -> {

            BuildNode build = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            BuildJobNode buildJob = buildJobFactory.getFromGraph(BUILD_NAME).get();

            assertThat(build.getBuildJob()).isEqualTo(buildJob);
            assertThat(build.getBuildNumber()).isEqualTo(BUILD_NUMBER);

        });
    }

    @Test
    public void testGetOrCreateFromGraph_SecondExecutionReturnsSameNodeInNewWrapper() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            BuildNode build2 = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);

            assertThat(build1).isEqualTo(build2);

        });
    }

    @Test
    public void testGetOrCreateFromGraph_DifferenDataReturnDifferentNodes() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            BuildNode build2 = buildFactory.getOrCreateFromGraph(BUILD_NAME, ANOTHER_BUILD_NUMBER);

            assertThat(build1).isNotEqualTo(build2);

        });
    }

    /* lookup for name and number */

    @Test
    public void testThatLookingUpNonExistingBuildWithNameAndNumberReturnsEmptyOptional_UnknownName() {
        inTransaction(() -> {
            Optional<BuildNode> build = buildFactory.getFromGraph(UNKNOWN_BUILD_NAME, 1);
            assertThat(build.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpNonExistingBuildWithNameAndNumberReturnsEmptyOptional_UnknownNumber() {
        inTransaction(() -> {
            buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            Optional<BuildNode> build = buildFactory.getFromGraph(BUILD_NAME, UNKNOWN_BUILD_NUMBER);
            assertThat(build.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingBuildWithNameAndNumberReturnsItAsAnOptional() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            BuildNode build2 = buildFactory.getFromGraph(BUILD_NAME, BUILD_NUMBER).get();

            assertThat(build2).isEqualTo(build1);

        });
    }

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingBuildWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<BuildNode> build = buildFactory.getFromGraph(UNKNOWN_ID);
            assertThat(build.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingBuildWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            BuildNode build2 = buildFactory.getFromGraph(build1.getId()).get();

            assertThat(build2).isEqualTo(build1);

        });
    }

}
