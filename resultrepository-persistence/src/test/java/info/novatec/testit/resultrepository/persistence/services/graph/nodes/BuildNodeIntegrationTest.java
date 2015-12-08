package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class BuildNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String BUILD_NAME = "buildName";

    private static final int BUILD_NUMBER = 1;
    private static final int ANOTHER_BUILD_NUMBER = 2;
    private static final int YET_ANOTHER_BUILD_NUMBER = 3;

    /* other builds */

    @Test
    public void testNextBuildBehaviour() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            BuildNode build2 = buildFactory.getOrCreateFromGraph(BUILD_NAME, ANOTHER_BUILD_NUMBER);
            BuildNode build3 = buildFactory.getOrCreateFromGraph(BUILD_NAME, YET_ANOTHER_BUILD_NUMBER);

            assertThat(build1.getNextBuild().get()).isEqualTo(build2);
            assertThat(build2.getNextBuild().get()).isEqualTo(build3);
            assertThat(build3.getNextBuild().isPresent()).isFalse();

        });
    }

    @Test
    public void testPreviousBuildBehaviour() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            BuildNode build2 = buildFactory.getOrCreateFromGraph(BUILD_NAME, ANOTHER_BUILD_NUMBER);
            BuildNode build3 = buildFactory.getOrCreateFromGraph(BUILD_NAME, YET_ANOTHER_BUILD_NUMBER);

            assertThat(build1.getPreviousBuild().isPresent()).isFalse();
            assertThat(build2.getPreviousBuild().get()).isEqualTo(build1);
            assertThat(build3.getPreviousBuild().get()).isEqualTo(build2);

        });
    }

    /* properties */

    @Test
    public void testThatBuildNumberCanBeRetrieved() {
        inTransaction(() -> {
            BuildNode build = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            assertThat(build.getBuildNumber()).isEqualTo(BUILD_NUMBER);
        });
    }

    /* build job */

    @Test
    public void testThatBuildJobCanBeRetrieved() {
        inTransaction(() -> {
            BuildJobNode buildJob = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildNode build = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            assertThat(build.getBuildJob()).isEqualTo(buildJob);
        });
    }

    @Test
    public void testThatBuildJobIsReturnedAsNullIfItDoesNotExist() {
        inTransaction(() -> {

            BuildJobNode buildJob = buildJobFactory.getOrCreateFromGraph(BUILD_NAME);
            BuildNode build = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);

            buildJob.deleteWithRelationships();

            assertThat(build.getBuildJob()).isNull();
            assertThat(build.getOptionalBuildJob().isPresent()).isFalse();

        });
    }

    /* tags */

    @Test
    public void testThatTagsCanBeRetrieved() {
        inTransaction(() -> {

            BuildNode build = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            TagNode tag1 = tagFactory.getOrCreateFromGraph("tag1");
            TagNode tag2 = tagFactory.getOrCreateFromGraph("tag2");
            TagNode tag3 = tagFactory.getOrCreateFromGraph("tag3");

            build.linkToTags(tag1, tag2);

            assertThat(build.getTags()).containsOnly(tag1, tag2).doesNotContain(tag3);

        });
    }

    /* test group results */

    @Test
    public void testThatTestGroupResultsCanBeRetrieved() {
        inTransaction(() -> {

            BuildNode build = buildFactory.getOrCreateFromGraph(BUILD_NAME, BUILD_NUMBER);
            TestGroupResultNode result1 = testGroupResultFactory.createInGraph();
            TestGroupResultNode result2 = testGroupResultFactory.createInGraph();
            TestGroupResultNode result3 = testGroupResultFactory.createInGraph();

            result1.setBuild(build);
            result2.setBuild(build);

            Set<TestGroupResultNode> testGroupResults = build.getTestGroupResults();
            assertThat(testGroupResults).containsOnly(result1, result2).doesNotContain(result3);

        });
    }

}
