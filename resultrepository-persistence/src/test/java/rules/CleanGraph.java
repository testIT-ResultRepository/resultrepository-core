package rules;

import org.junit.rules.ExternalResource;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;


public class CleanGraph extends ExternalResource {

    private GraphDatabaseService testGraph;

    public CleanGraph(GraphDatabaseService testGraph) {
        this.testGraph = testGraph;
    }

    @Override
    protected void before() {
        clean(testGraph);
    }

    @Override
    protected void after() {
        // nothing to do after
    }

    public static void clean(GraphDatabaseService graph) {
        try (Transaction tx = graph.beginTx()) {
            GlobalGraphOperations go = GlobalGraphOperations.at(graph);
            go.getAllRelationships().forEach(rel -> rel.delete());
            go.getAllNodes().forEach(node -> node.delete());
            tx.success();
        }
    }

}
