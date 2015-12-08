package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.Test;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#TEST} labeled node and provides
 * different operations for interacting with it as a test.
 */
public class TestNode extends AbstractNodeWrapper implements Test {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_NAME = "name";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain TestNode test} using the given value. Calling
     * this constructor will create a new {@linkplain Node node} in the graph.
     * If you want to wrap an existing {@linkplain Node node} as a
     * {@linkplain TestNode test} use {@linkplain #TestNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     * @param name the name of this {@linkplain TestNode test}
     */
    protected TestNode(GraphDatabaseService graphDb, String name) {
        super(graphDb, Labels.TEST);
        setRequiredProperty(PROPERTY_NAME, name);
    }

    /**
     * Creates a new {@linkplain TestNode test} by wrapping the given
     * {@linkplain Node node}. Validates if the given node has the required
     * label {@linkplain Labels#TEST}.
     *
     * @param node the node to wrap as a {@linkplain TestNode test}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST} node
     */
    protected TestNode(Node node) {
        super(node, Labels.TEST);
    }

    @Override
    public String getName() {
        return getStringProperty(PROPERTY_NAME).get();
    }

    /**
     * Returns all {@linkplain TestResultNode test results} which are linked to
     * this {@linkplain TestNode test} as a {@linkplain Set set}.
     * <p>
     * This is a comfort method for the usecases where all
     * {@linkplain TestResultNode test results} are needed. The preferred way to
     * retrieve the {@linkplain TestResultNode test results} is to use the
     * {@linkplain Stream stream} returning {@linkplain #getTestResultsStream()}
     * method.
     *
     * @return the {@linkplain TestResultNode test results} as a
     * {@linkplain Set set}
     */
    public Set<TestResultNode> getTestResults() {
        return getTestResultsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all {@linkplain TestResultNode test results} which are linked to
     * this {@linkplain TestNode test} as a {@linkplain Stream stream}.
     *
     * @return the {@linkplain TestResultNode test results} as a
     * {@linkplain Stream stream}
     */
    public Stream<TestResultNode> getTestResultsStream() {
        return getIncomingRelationships(RelationshipTypes.IS_RESULT_OF_TEST).map(
            rel -> TestResultNodeFactory.wrap(rel.getStartNode()));
    }

    /**
     * Returns the latest (chronologically) {@linkplain TestResultNode test
     * result} of this {@linkplain TestNode test} as an {@linkplain Optional optional}.
     *
     * @return an optional {@linkplain TestResultNode test result}
     */
    public Optional<TestResultNode> getLatestResult() {
        /* TODO this method's performance will decrease with each linked result
         * of this test node */
        return getTestResultsStream().max((a, b) -> Long.compare(a.getCreationTimestamp(), b.getCreationTimestamp()));
    }

}
