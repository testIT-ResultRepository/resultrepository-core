package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TagNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String TAG = "tag";
    private static final String ANOTHER_TAG = "another";

    /* properties */

    @Test
    public void testThatValuCanBeSetAndGet() {
        inTransaction(() -> {

            TagNode tag = tagFactory.getOrCreateFromGraph(TAG);

            tag.setRequiredProperty(TagNode.PROPERTY_VALUE, ANOTHER_TAG);
            assertThat(tag.getValue(), is(ANOTHER_TAG));

        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatValuIsARequiredAttribute() {
        inTransaction(() -> {
            TagNode r = tagFactory.getOrCreateFromGraph(TAG);
            r.setRequiredProperty(TagNode.PROPERTY_VALUE, null);
        });
    }

    /* number of references */

    @Test
    public void testThatNumberOfReferencesIsAccurate() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph("BuildJob1", 1);
            BuildNode build2 = buildFactory.getOrCreateFromGraph("BuildJob2", 2);

            TestGroupResultNode testGroupResult1 = testGroupResultFactory.createInGraph();
            TestGroupResultNode testGroupResult2 = testGroupResultFactory.createInGraph();

            TestResultNode testResult1 = testResultFactory.createInGraph();
            TestResultNode testResult2 = testResultFactory.createInGraph();

            TagNode tag = tagFactory.getOrCreateFromGraph(TAG);

            build1.linkToTag(tag);
            build2.linkToTag(tag);

            testGroupResult1.linkToTag(tag);
            testGroupResult2.linkToTag(tag);

            testResult1.linkToTag(tag);
            testResult2.linkToTag(tag);

            assertThat(tag.getNumberOfReferences()).isEqualTo(6);

        });
    }

    /* tagged entities */

    @Test
    public void testRetrievalOfTaggedBuilds() {
        inTransaction(() -> {

            BuildNode build1 = buildFactory.getOrCreateFromGraph("BuildJob1", 1);
            BuildNode build2 = buildFactory.getOrCreateFromGraph("BuildJob2", 2);
            BuildNode build3 = buildFactory.getOrCreateFromGraph("BuildJob3", 3);

            TagNode tag = tagFactory.getOrCreateFromGraph(TAG);
            build1.linkToTag(tag);
            build2.linkToTag(tag);

            Set<BuildNode> builds = tag.getBuilds();
            assertThat(builds).containsOnly(build1, build2).doesNotContain(build3);

        });
    }

    @Test
    public void testRetrievalOfTaggedTestGroupResults() {
        inTransaction(() -> {

            TestGroupResultNode testGroupResult1 = testGroupResultFactory.createInGraph();
            TestGroupResultNode testGroupResult2 = testGroupResultFactory.createInGraph();
            TestGroupResultNode testGroupResult3 = testGroupResultFactory.createInGraph();

            TagNode tag = tagFactory.getOrCreateFromGraph(TAG);
            testGroupResult1.linkToTag(tag);
            testGroupResult2.linkToTag(tag);

            Set<TestGroupResultNode> testGroupResults = tag.getTestGroupResults();
            assertThat(testGroupResults).containsOnly(testGroupResult1, testGroupResult2).doesNotContain(testGroupResult3);

        });
    }

    @Test
    public void testRetrievalOfTaggedTestResult() {
        inTransaction(() -> {

            TestResultNode testResult1 = testResultFactory.createInGraph();
            TestResultNode testResult2 = testResultFactory.createInGraph();
            TestResultNode testResult3 = testResultFactory.createInGraph();

            TagNode tag = tagFactory.getOrCreateFromGraph(TAG);
            testResult1.linkToTag(tag);
            testResult2.linkToTag(tag);

            Set<TestResultNode> testResults = tag.getTestResults();
            assertThat(testResults).containsOnly(testResult1, testResult2).doesNotContain(testResult3);

        });
    }

}
