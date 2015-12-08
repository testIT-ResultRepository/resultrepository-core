package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class BuildJobNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String BUILD_NAME = "buildName";

    @Test(expected = IllegalArgumentException.class)
    public void testThatNameIsARequiredProperty() {
        inTransaction(() -> {
            new BuildJobNode(testGraph, null);
        });
    }

    /* builds */

    @Test
    public void testThatBuildsAreReturnedAsAnOrderedList() {
        inTransaction(() -> {

            BuildJobNode buildJob = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 1);
            BuildNode build2 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 2);
            BuildNode build3 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 3);

            List<BuildNode> builds = buildJob.getBuildsInOrder();

            assertThat(builds).containsExactly(build1, build2, build3);

        });
    }

    @Test
    public void testThatGetLatestBuildsAppliesLimit() {
        inTransaction(() -> {

            BuildJobNode buildJob = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 1);
            BuildNode build2 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 2);
            BuildNode build3 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 3);

            List<BuildNode> builds = buildJob.getLatestBuilds(2);

            assertThat(builds).doesNotContain(build1).containsExactly(build2, build3);

        });
    }

    @Test
    public void testThatGetLatestBuildsLowerBoundIsZero() {
        inTransaction(() -> {

            buildFactory.getOrCreateFromGraph(BUILD_NAME, 1);
            buildFactory.getOrCreateFromGraph(BUILD_NAME, 2);
            buildFactory.getOrCreateFromGraph(BUILD_NAME, 3);

            BuildJobNode buildJob = buildJobFactory.getFromGraph(BUILD_NAME).get();
            List<BuildNode> builds = buildJob.getLatestBuilds(-100);

            assertThat(builds).isEmpty();

        });
    }

    @Test
    public void testThatGetLatestBuildsWithoutHavingAnyReturnsEmptryList() {
        inTransaction(() -> {

            BuildJobNode buildJob = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            List<BuildNode> builds = buildJob.getLatestBuilds(100);

            assertThat(builds).isEmpty();

        });
    }

    @Test
    public void testThatBuildsCanBeReadAsAnUnorderedSet() {
        inTransaction(() -> {

            BuildJobNode buildJob = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 1);
            BuildNode build2 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 2);
            BuildNode build3 = buildFactory.getOrCreateFromGraph(BUILD_NAME, 3);

            Set<BuildNode> builds = buildJob.getBuilds();

            assertThat(builds).containsOnly(build1, build2, build3);

        });
    }

}
