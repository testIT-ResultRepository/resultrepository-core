package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#TEST_RESULT_DETAIL} labeled node and
 * provides different operations for interacting with it as a test result
 * detail.
 */
public class TestResultDetailNode extends AbstractNodeWrapper implements TestResultDetail {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_TYPE = "type";
    public static final String PROPERTY_MESSAGE = "message";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain TestResultDetailNode test result detail}.
     * Calling this constructor will create a new {@linkplain Node node} in the
     * graph. If you want to wrap an existing {@linkplain Node node} as a
     * {@linkplain TestResultDetailNode test result detail} use
     * {@linkplain #TestResultDetailNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     */
    protected TestResultDetailNode(GraphDatabaseService graphDb) {
        super(graphDb, Labels.TEST_RESULT_DETAIL);
    }

    /**
     * Creates a new {@linkplain TestResultDetailNode test result detail} by
     * wrapping the given {@linkplain Node node}. Validates if the given node
     * has the required label {@linkplain Labels#TEST_RESULT_DETAIL}.
     *
     * @param node the node to wrap as a {@linkplain TestGroupResultNode test
     * group result}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_RESULT_DETAIL} node
     */
    protected TestResultDetailNode(Node node) {
        super(node, Labels.TEST_RESULT_DETAIL);
    }

    /**
     * Sets the {@linkplain DetailType type} of this
     * {@linkplain TestResultDetailNode test result detail}. If null is given as
     * a type it defaults to {@linkplain DetailType#INFORMATION}.
     *
     * @param resultType the {@linkplain DetailType type} to set
     * @return the same {@linkplain TestResultDetailNode test result detail}
     * instance to support fluent API operations
     */
    public TestResultDetailNode setType(DetailType resultType) {
        setProperty(PROPERTY_TYPE, resultType != null ? resultType.name() : String.valueOf(DetailType.INFORMATION));
        return this;
    }

    /**
     * Returns the {@linkplain DetailType type} of this
     * {@linkplain TestResultDetailNode test result detail}. If no type
     * information is available it will default to
     * {@linkplain DetailType#INFORMATION}.
     *
     * @return the {@linkplain DetailType type}
     */
    @Override
    public DetailType getType() {
        return getStringProperty(PROPERTY_TYPE).map(type -> DetailType.valueOf(type)).orElse(DetailType.INFORMATION);
    }

    /**
     * Sets the message of this {@linkplain TestResultDetailNode test result
     * detail}. May be null to remove the message from the node.
     *
     * @param message the message
     * @return the same {@linkplain TestResultDetailNode test result detail}
     * instance to support fluent API operations
     */
    public TestResultDetailNode setMessage(String message) {
        setProperty(PROPERTY_MESSAGE, message);
        return this;
    }

    /**
     * Returns the message of this {@linkplain TestResultDetailNode test result
     * detail}. May return null if no message is available.
     *
     * @return the message
     */
    @Override
    public String getMessage() {
        return getOptionalMessage().orElse(null);
    }

    /**
     * Returns the message of this {@linkplain TestResultDetailNode test result
     * detail} as an {@linkplain Optional optional}.
     *
     * @return the message
     */
    public Optional<String> getOptionalMessage() {
        return getStringProperty(PROPERTY_MESSAGE);
    }

    /**
     * Returns the {@linkplain TestResultNode test result} this
     * {@linkplain TestResultDetailNode test result detail} belongs to as an
     * {@linkplain Optional optional}.
     *
     * @return the test result
     */
    public Optional<TestResultNode> getTestResult() {
        return getSingleIncomingRelationship(RelationshipTypes.CONTAINS).map(
            rel -> TestResultNodeFactory.wrap(rel.getStartNode()));
    }

}
