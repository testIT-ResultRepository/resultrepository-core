package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#TAG} labeled node and provides
 * different operations for interacting with it as a tag.
 */
public class TagNode extends AbstractNodeWrapper implements Tag {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_VALUE = "value";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain TagNode tag} using the given value. Calling
     * this constructor will create a new {@linkplain Node node} in the graph.
     * If you want to wrap an existing {@linkplain Node node} as a
     * {@linkplain TagNode tag} use {@linkplain #TagNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     * @param value the value of this {@linkplain TagNode tag}
     */
    protected TagNode(GraphDatabaseService graphDb, String value) {
        super(graphDb, Labels.TAG);
        setRequiredProperty(PROPERTY_VALUE, value);
    }

    /**
     * Creates a new {@linkplain TagNode tag} by wrapping the given
     * {@linkplain Node node}. Validates if the given node has the required
     * label {@linkplain Labels#TAG}.
     *
     * @param node the node to wrap as a {{@linkplain TagNode tag}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TAG} node
     */
    protected TagNode(Node node) {
        super(node, Labels.TAG);
    }

    @Override
    public String getValue() {
        return getStringProperty(PROPERTY_VALUE).get();
    }

    /**
     * Returns the number of references to this {@linkplain TagNode tag}. A reference
     * in this context is equal to the incoming relationships of this
     * {@linkplain TagNode tag' node}.
     *
     * @return number of references
     */
    public Integer getNumberOfReferences() {
        return getNode().getDegree(Direction.INCOMING);
    }

    /**
     * Returns all {@linkplain BuildNode builds} which are tagged with this
     * {@linkplain TagNode tag} as a {@linkplain Set set}.
     * <p>
     * This is a comfort method for the usecases where all
     * {@linkplain BuildNode builds} are needed. The preferred way to retrieve
     * the {@linkplain BuildNode builds} is to use the {@linkplain Stream
     * stream} returning {@linkplain #getBuildsStream()} method.
     *
     * @return the {@linkplain BuildNode builds} as a {@linkplain Set set}
     */
    public Set<BuildNode> getBuilds() {
        return getBuildsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all {@linkplain BuildNode builds} which are tagged with this
     * {@linkplain TagNode tag} as a {@linkplain Stream stream}.
     *
     * @return the {@linkplain BuildNode builds} as a {@linkplain Stream stream}
     */
    public Stream<BuildNode> getBuildsStream() {
        return getRelationships(RelationshipTypes.IS_USED_BY_BUILD).map(
            rel -> BuildNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

    /**
     * Returns all {@linkplain TestGroupResultNode test group results} which are
     * tagged with this {@linkplain TagNode tag} as a {@linkplain Set set}.
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
     * tagged with this {@linkplain TagNode tag} as a {@linkplain Stream stream}.
     *
     * @return the {@linkplain TestGroupResultNode test group results} as a
     * {@linkplain Stream stream}
     */
    public Stream<TestGroupResultNode> getTestGroupResultsStream() {
        return getRelationships(RelationshipTypes.IS_USED_BY_TEST_GROUP_RESULT).map(
            rel -> TestGroupResultNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

    /**
     * Returns all {@linkplain TestResultNode test results} which are tagged
     * with this {@linkplain TagNode tag} as a {@linkplain Set set}.
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
     * Returns all {@linkplain TestResultNode test results} which are tagged
     * with this {@linkplain TagNode tag} as a {@linkplain Stream stream}.
     *
     * @return the {@linkplain TestResultNode test results} as a
     * {@linkplain Stream stream}
     */
    public Stream<TestResultNode> getTestResultsStream() {
        return getRelationships(RelationshipTypes.IS_USED_BY_TEST_RESULT).map(
            rel -> TestResultNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

}
