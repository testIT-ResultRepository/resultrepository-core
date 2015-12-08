package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class BuildJobNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String BUILD_NAME = "buildName";
    private static final String ANOTHER_BUILD_NAME = "anotherBuildName";
    private static final String UNKNOWN_BUILD_NAME = "unknownBuildName";

    private static final long UNKNOWN_ID = -1L;

    /* get or create */

    @Test
    public void testGetOrCreateFromGraph_CreatesCorrectNodeOnFirstExecution() {
        inTransaction(() -> {
            BuildJobNode buildJob = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            assertThat(buildJob.getName()).isEqualTo(BUILD_NAME);
        });
    }

    @Test
    public void testGetOrCreateFromGraph_SecondExecutionReturnsSameNodeInNewWrapper() {
        inTransaction(() -> {

            BuildJobNode buildJob1 = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildJobNode buildJob2 = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);

            assertThat(buildJob2).isEqualTo(buildJob1);

        });
    }

    @Test
    public void testGetOrCreateFromGraph_DifferenDataReturnDifferentNodes() {
        inTransaction(() -> {

            BuildJobNode buildJob1 = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildJobNode buildJob2 = buildJobFactory.getOrCreateFromGraph(ANOTHER_BUILD_NAME);

            assertThat(buildJob2).isNotEqualTo(buildJob1);

        });
    }

    /* lookup for name */

    @Test
    public void testThatLookingUpNonExistingBuildJobWithNameReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<BuildJobNode> buildJob = buildJobFactory.getFromGraph(UNKNOWN_BUILD_NAME);
            assertThat(buildJob.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingBuildJobWithNameReturnsItAsAnOptional() {
        inTransaction(() -> {

            BuildJobNode buildJob1 = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildJobNode buildJob2 = buildJobFactory.getFromGraph(BUILD_NAME).get();

            assertThat(buildJob2).isEqualTo(buildJob1);

        });
    }

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingBuildJobWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<BuildJobNode> buildJob = buildJobFactory.getFromGraph(UNKNOWN_ID);
            assertThat(buildJob.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingBuildJobWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            BuildJobNode buildJob1 = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildJobNode buildJob2 = buildJobFactory.getFromGraph(buildJob1.getId()).get();

            assertThat(buildJob2).isEqualTo(buildJob1);

        });
    }

    /* get all */

    @Test
    public void testThatGettingAllBuildJobsFromEmptyGraphReturnsEmptyStream() {
        inTransaction(() -> {
            assertEmpty(buildJobFactory.getAllFromGraph());
        });
    }

    @Test
    public void testThatGettingAllBuildJobsFromGraphReturnsThemAllAsAStream() {
        inTransaction(() -> {

            BuildJobNode buildJob1 = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildJobNode buildJob2 = buildJobFactory.getOrCreateFromGraph(ANOTHER_BUILD_NAME);

            Stream<BuildJobNode> buildJobs = buildJobFactory.getAllFromGraph();

            assertThat(buildJobs.collect(Collectors.toSet())).containsOnly(buildJob1, buildJob2);

        });
    }

}
