package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.MetadataValue;
import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#METADATA_VALUE} labeled node and
 * provides different operations for interacting with it as a meta data value.
 */
public class MetadataValueNode extends AbstractNodeWrapper implements MetadataValue {

    public static final String PATTERN_SPECIALIZED_METADATAVALUE =
        MetadataPath.PATTERN_METADATAVALUE + "(:" + MetadataPath.PATTERN_METADATAVALUE + ")+";

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_VALUE = "value";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain MetadataValueNode meta data value} using the
     * given value. Calling this constructor will create a new {@linkplain Node
     * node} in the graph. If you want to wrap an existing {@linkplain Node
     * node} as a {@linkplain MetadataValueNode meta data value} use
     * {@linkplain #MetadataValueNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     * @param value the value of this {@linkplain MetadataValueNode meta data
     * value}
     */
    protected MetadataValueNode(GraphDatabaseService graphDb, String value) {
        super(graphDb, Labels.METADATA_VALUE);
        setRequiredProperty(PROPERTY_VALUE, value);
    }

    /**
     * Creates a new {@linkplain MetadataValueNode meta data value} by wrapping
     * the given {@linkplain Node node}. Validates if the given node has the
     * required label {@linkplain Labels#METADATA_VALUE}.
     *
     * @param node the node to wrap as a {@linkplain MetadataValueNode meta data
     * value}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#METADATA_VALUE} node
     */
    protected MetadataValueNode(Node node) {
        super(node, Labels.METADATA_VALUE);
    }

    @Override
    public MetadataKindNode getMetadataKind() {
        return getOptionalMetadataKind().orElse(null);
    }

    /**
     * Returns this {@linkplain MetadataValueNode meta data value's} kind. This is
     * essentially the same operation as {@linkplain #getMetadataKind()} with
     * the difference of using Java 8's Optional to avoid returning null.
     *
     * @return the {@linkplain MetadataKindNode meta data kind} as an
     * {@linkplain Optional}
     */
    public Optional<MetadataKindNode> getOptionalMetadataKind() {

        MetadataValueNode generalization = this;
        Optional<MetadataValueNode> nextGeneralization = getGeneralization();
        while (nextGeneralization.isPresent()) {
            generalization = nextGeneralization.get();
            nextGeneralization = generalization.getGeneralization();
        }

        return generalization.getSingleIncomingRelationship(RelationshipTypes.HAS_METADATA_VALUE)
            .map(rel -> MetadataKindNodeFactory.wrap(rel.getStartNode()));

    }

    @Override
    public String getValue() {
        return getStringProperty(PROPERTY_VALUE).get();
    }

    /**
     * Returns a {@linkplain MetadataPath meta data path} representation of the path
     * from the meta data kind to this value.
     *
     * @return the {@linkplain MetadataPath meta data path}
     */
    public MetadataPath getPath() {
        return MetadataPath.from(this);
    }

    /**
     * Returns a more 'general' {@linkplain MetadataValueNode meta data value} then
     * this one. As an example this could be <code>windows</code> for the value
     * <code>windows:7</code>. More formally it returns the
     * {@linkplain MetadataValueNode meta data value} one level above the current
     * one.
     *
     * @return the generalization as an {@linkplain Optional}, since there might not
     * be a generalization.
     */
    public Optional<MetadataValueNode> getGeneralization() {
        return getSingleIncomingRelationship(RelationshipTypes.SPECIALIZES_TO).map(
            rel -> MetadataValueNodeFactory.wrap(rel.getStartNode()));
    }

    /**
     * Returns all the more 'specialized' {@linkplain MetadataValueNode meta data
     * value} then this one as a {@linkplain Set set}. As an example this could be
     * <code>windows:7</code> and <code>windows:xp</code> for the value
     * <code>windows</code>. More formally it returns the
     * {@linkplain MetadataValueNode meta data values} one level below the current
     * one.
     * <p>
     * This is a comfort method for the usecases where all
     * {@linkplain MetadataValueNode meta data value} are needed. The preferred way
     * to retrieve the {@linkplain MetadataValueNode meta data value} is to use the
     * {@linkplain Stream stream} returning
     * {@linkplain #getSpecializationsStream()} method.
     *
     * @return the specializations as a {@linkplain Set set}
     */
    public Set<MetadataValueNode> getSpecializations() {
        return getSpecializationsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all the more 'specialized' {@linkplain MetadataValueNode meta data
     * value} then this one as a {@linkplain Stream stream}. As an example this could
     * be <code>windows:7</code> and <code>windows:xp</code> for the value
     * <code>windows</code>. More formally it returns the
     * {@linkplain MetadataValueNode meta data values} one level below the current
     * one.
     *
     * @return the specializations as a {@linkplain Stream stream}
     */
    public Stream<MetadataValueNode> getSpecializationsStream() {
        return getOutgoingRelationships(RelationshipTypes.SPECIALIZES_TO).map(
            rel -> MetadataValueNodeFactory.wrap(rel.getEndNode()));
    }

}
