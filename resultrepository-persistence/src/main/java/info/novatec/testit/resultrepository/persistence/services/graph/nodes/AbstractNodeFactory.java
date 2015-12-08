package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.metrics.api.metrics.CounterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.Metrics;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;


public abstract class AbstractNodeFactory {

    private final CounterMetric totalCreatedCounter = counter("totalCreated");
    private final CounterMetric totalLookedUpCounter = counter("totalLookedUp");
    private final CounterMetric totalResolvedCounter = counter("totalResolved");
    private final CounterMetric totalLookedUpByIdCounter = counter("totalLookedUp", "byId");
    private final CounterMetric totalResolvedByIdCounter = counter("totalResolved", "byId");

    @Autowired
    private GraphDatabaseService graphDatabase;
    @Autowired
    private MetricsManager metricsManager;

    protected Optional<Node> getSingleNodeFromIndex(Label label, String property, String value) {
        return Optional.ofNullable(graphDatabase.findNode(label, property, value));
    }

    protected Stream<Node> getNodesForLabel(Label label) {
        ResourceIterable<Node> iterable = () -> graphDatabase.findNodes(label);
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /* metrics support */

    private CounterMetric counter(String... nameParts) {
        return Metrics.counterMetric(getClass(), nameParts);
    }

    protected void incrementTotalCreatedCounter() {
        metrics().increment(totalCreatedCounter);
    }

    protected void incrementTotalLookedUpByName() {
        metrics().increment(totalLookedUpCounter);
    }

    protected void incrementTotalResolvedByName() {
        metrics().increment(totalResolvedCounter);
    }

    protected void incrementTotalLookedUpById() {
        metrics().increment(totalLookedUpByIdCounter);
    }

    protected void incrementTotalResolvedById() {
        metrics().increment(totalResolvedByIdCounter);
    }

    /* getters */

    protected GraphDatabaseService graph() {
        return graphDatabase;
    }

    protected MetricsManager metrics() {
        return metricsManager;
    }

    protected void setGraphDatabase(GraphDatabaseService graphDatabase) {
        this.graphDatabase = graphDatabase;
    }

}
