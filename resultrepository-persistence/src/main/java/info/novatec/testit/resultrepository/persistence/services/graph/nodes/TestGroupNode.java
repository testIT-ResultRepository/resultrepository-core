package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.TestGroup;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#TEST_GROUP} labeled node and provides
 * different operations for interacting with it as a test group.
 */
public class TestGroupNode extends AbstractNodeWrapper implements TestGroup {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_NAME = "name";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain TestGroupNode test group} using the given
     * value. Calling this constructor will create a new {@linkplain Node node}
     * in the graph. If you want to wrap an existing {@linkplain Node node} as a
     * {@linkplain TestGroupNode test group} use
     * {@linkplain #TestGroupNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     * @param name the name of this {@linkplain TestGroupNode test group}
     */
    protected TestGroupNode(GraphDatabaseService graphDb, String name) {
        super(graphDb, Labels.TEST_GROUP);
        setRequiredProperty(PROPERTY_NAME, name);
    }

    /**
     * Creates a new {@linkplain TestGroupNode test group} by wrapping the given
     * {@linkplain Node node}. Validates if the given node has the required
     * label {@linkplain Labels#TEST_GROUP}.
     *
     * @param node the node to wrap as a {@linkplain TestGroupNode test group}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_GROUP} node
     */
    protected TestGroupNode(Node node) {
        super(node, Labels.TEST_GROUP);
    }

    @Override
    public String getName() {
        return getStringProperty(PROPERTY_NAME).get();
    }

    /**
     * Returns all {@linkplain TestGroupResultNode test group results} which are
     * linked to this {@linkplain TestGroupNode test group} as a {@linkplain Set set}
     * .
     * <p>
     * This is a comfort method for the usecases where all
     * {@linkplain TestGroupResultNode test group results} are needed. The
     * preferred way to retrieve the {@linkplain TestGroupResultNode test group
     * results} is to use the {@linkplain Stream stream} returning
     * {@linkplain #getTestGroupResultsStream()} method.
     *
     * @return the {@linkplain TestGroupResultNode test group results} as a
     * {@linkplain Set set}
     */
    public Set<TestGroupResultNode> getTestGroupResults() {
        return getTestGroupResultsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all {@linkplain TestGroupResultNode test group results} which are
     * linked to this {@linkplain TestGroupNode test group} as a {@linkplain Stream
     * stream}.
     *
     * @return the {@linkplain TestGroupResultNode test group results} as a
     * {@linkplain Stream stream}
     */
    public Stream<TestGroupResultNode> getTestGroupResultsStream() {
        return getIncomingRelationships(RelationshipTypes.IS_RESULT_OF_TEST_GROUP).map(
            rel -> TestGroupResultNodeFactory.wrap(rel.getStartNode()));
    }

    /**
     * Returns the latest (chronologically) {@linkplain TestResultNode test
     * result} of this {@linkplain TestGroupNode test group} as an {@linkplain Optional
     * optional}.
     *
     * @return an optional {@linkplain TestResultNode test result}
     */
    public Optional<TestGroupResultNode> getLatestResult() {
        return getTestGroupResultsStream().max((a, b) -> Long.compare(a.getCreationTimestamp(), b.getCreationTimestamp()));
    }

}
