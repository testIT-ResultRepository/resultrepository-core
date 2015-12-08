package info.novatec.testit.resultrepository.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.metrics.config.DeactivatedMetricsConfiguration;
import info.novatec.testit.resultrepository.persistence.config.PersistenceConfiguration;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildJobNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.MetadataKindNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.MetadataValueNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNodeFactory;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { PersistenceConfiguration.class, DeactivatedMetricsConfiguration.class })
@TestPropertySource(properties = { "resultrepository.persistence.data-folder=target/data/graphTest" })
public abstract class AbstractPersistenceIntegrationTest {

    @Autowired
    protected GraphDatabaseService testGraph;

    @Autowired
    protected BuildJobNodeFactory buildJobFactory;
    @Autowired
    protected BuildNodeFactory buildFactory;
    @Autowired
    protected MetadataKindNodeFactory metadataKindFactory;
    @Autowired
    protected MetadataValueNodeFactory metadataValueFactory;
    @Autowired
    protected TagNodeFactory tagFactory;
    @Autowired
    protected TestGroupNodeFactory testGroupFactory;
    @Autowired
    protected TestGroupResultNodeFactory testGroupResultFactory;
    @Autowired
    protected TestNodeFactory testFactory;
    @Autowired
    protected TestResultDetailNodeFactory testResultDetailFactory;
    @Autowired
    protected TestResultNodeFactory testResultFactory;

    @Before
    public void cleanGraph() {
        inTransaction(() -> {
            GlobalGraphOperations go = GlobalGraphOperations.at(testGraph);
            go.getAllRelationships().forEach(rel -> rel.delete());
            go.getAllNodes().forEach(node -> node.delete());
        });
    }

    protected void inTransaction(Runnable runnable) {
        try (Transaction tx = testGraph.beginTx()) {
            runnable.run();
            tx.success();
        }
    }

    protected <T> T inTransaction(Supplier<T> supplier) {
        try (Transaction tx = testGraph.beginTx()) {
            T value = supplier.get();
            tx.success();
            return value;
        }
    }

    protected void assertEmpty(Stream<?> stream) {
        assertThat(stream.count(), is(0L));
    }

    /* utilities */

    protected TestGroupResultData createTestGroupResult() {
        return inTransaction(() -> new TestGroupResultData(testGroupResultFactory.createInGraph()));
    }

    protected TestGroupResultData createTestGroupResult(TestGroupData testGroup) {
        return inTransaction(() -> {
            TestGroupNode testGroupNode = testGroupFactory.getFromGraph(testGroup.getId()).get();
            return new TestGroupResultData(testGroupResultFactory.createInGraph().setTestGroup(testGroupNode));
        });
    }

    protected TestGroupResultData createTestGroupResult(TagData... tags) {
        return inTransaction(() -> {
            TestGroupResultNode testGroupResultNode = testGroupResultFactory.createInGraph();
            Arrays.stream(tags)
                .map(tag -> tagFactory.getOrCreateFromGraph(tag.getValue()))
                .forEach(tag -> testGroupResultNode.linkToTag(tag));
            return new TestGroupResultData(testGroupResultNode);
        });
    }

    protected TestGroupResultData createTestGroupResult(BuildData build) {
        return inTransaction(() -> {
            BuildNode buildNode = buildFactory.getFromGraph(build.getId()).get();
            return new TestGroupResultData(testGroupResultFactory.createInGraph().setBuild(buildNode));
        });
    }

    protected TestResultData createTestResult() {
        return inTransaction(() -> new TestResultData(testResultFactory.createInGraph()));
    }

    protected TestResultData createTestResult(TestData test) {
        return inTransaction(() -> {
            TestNode testNode = testFactory.getFromGraph(test.getId()).get();
            return new TestResultData(testResultFactory.createInGraph().setTest(testNode));
        });
    }

    protected TestResultData createTestResult(TestGroupResultData testGroupResult) {
        return inTransaction(() -> {
            TestGroupResultNode testGroupResultNode = testGroupResultFactory.getFromGraph(testGroupResult.getId()).get();
            TestResultNode testResultNode = testResultFactory.createInGraph();
            testGroupResultNode.addTestResult(testResultNode);
            return new TestResultData(testResultNode);
        });
    }

    protected TestResultData createTestResult(TagData... tags) {
        return inTransaction(() -> {
            TestResultNode testResultNode = testResultFactory.createInGraph();
            Arrays.stream(tags)
                .map(tag -> tagFactory.getOrCreateFromGraph(tag.getValue()))
                .forEach(tag -> testResultNode.linkToTag(tag));
            return new TestResultData(testResultNode);
        });
    }

    protected TestResultData createTestResult(TestData test, ResultStatus status) {
        return inTransaction(() -> {
            TestNode testNode = testFactory.getFromGraph(test.getId()).get();
            return new TestResultData(testResultFactory.createInGraph().setTest(testNode).setStatus(status));
        });
    }

    protected TestResultData createTestResult(TestGroupResultData testGroupResult, ResultStatus status) {
        return inTransaction(() -> {
            TestGroupResultNode testGroupResultNode = testGroupResultFactory.getFromGraph(testGroupResult.getId()).get();
            TestResultNode testResultNode = testResultFactory.createInGraph().setStatus(status);
            testGroupResultNode.addTestResult(testResultNode);
            return new TestResultData(testResultNode);
        });
    }

    protected TestResultDetailData createTestResultDetail() {
        return inTransaction(() -> new TestResultDetailData(testResultDetailFactory.createInGraph()));
    }

    protected TestResultDetailData createTestResultDetail(TestResultData testResult) {
        return createTestResultDetail(testResult, null);
    }

    protected TestResultDetailData createTestResultDetail(TestResultData testResult, DetailType type) {
        return inTransaction(() -> {
            TestResultDetailNode testResultDetailNode = testResultDetailFactory.createInGraph().setType(type);
            TestResultNode testResultNode = testResultFactory.getFromGraph(testResult.getId()).get();
            testResultNode.addTestResultDetail(testResultDetailNode);
            return new TestResultDetailData(testResultDetailNode);
        });
    }

    protected BuildJobData createBuildJob(String name) {
        return inTransaction(() -> new BuildJobData(buildJobFactory.getOrCreateFromGraph(name)));
    }

    protected BuildData createBuild(String name, int number) {
        return inTransaction(() -> new BuildData(buildFactory.getOrCreateFromGraph(name, number)));
    }

    protected BuildData createBuild(String name, int number, TagData... tags) {
        return inTransaction(() -> {
            BuildNode buildNode = buildFactory.getOrCreateFromGraph(name, number);
            Arrays.stream(tags)
                .map(tag -> tagFactory.getFromGraph(tag.getId()).get())
                .forEach(tag -> buildNode.linkToTag(tag));
            return new BuildData(buildNode);
        });
    }

    protected TagData createTag(String value) {
        return inTransaction(() -> new TagData(tagFactory.getOrCreateFromGraph(value)));
    }

    protected TestData createTest(String name) {
        return inTransaction(() -> new TestData(testFactory.getOrCreateFromGraph(name)));
    }

    protected TestGroupData createTestGroup(String name) {
        return inTransaction(() -> new TestGroupData(testGroupFactory.getOrCreateFromGraph(name)));
    }

}
