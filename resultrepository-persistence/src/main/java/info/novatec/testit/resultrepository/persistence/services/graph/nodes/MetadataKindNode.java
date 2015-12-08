package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.MetadataKind;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#METADATA_KIND} labeled node and
 * provides different operations for interacting with it as a meta data kind.
 */
public class MetadataKindNode extends AbstractNodeWrapper implements MetadataKind {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_DESCRIPTION = "description";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain MetadataKindNode meta data kind} using the
     * given name. Calling this constructor will create a new {@linkplain Node
     * node} in the graph. If you want to wrap an existing {@linkplain Node
     * node} as a {@linkplain MetadataKindNode meta data kind} use
     * {@linkplain #MetadataKindNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     * @param name the name of this {@linkplain MetadataKindNode meta data kind}
     */
    protected MetadataKindNode(GraphDatabaseService graphDb, String name) {
        super(graphDb, Labels.METADATA_KIND);
        setRequiredProperty(PROPERTY_NAME, name);
    }

    /**
     * Creates a new {@linkplain MetadataKindNode meta data kind} by wrapping
     * the given {@linkplain Node node}. Validates if the given node has the
     * required label {@linkplain Labels#METADATA_KIND}.
     *
     * @param node the node to wrap as a {@linkplain MetadataKindNode meta data
     * kind}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#METADATA_KIND} node
     */
    protected MetadataKindNode(Node node) {
        super(node, Labels.METADATA_KIND);
    }

    @Override
    public String getName() {
        return getStringProperty(PROPERTY_NAME).get();
    }

    /**
     * Changes the description of this {@linkplain MetadataKindNode meta data
     * kind}. May be null in order to remove the description property
     * completely.
     *
     * @param description the description to set
     * @return the same {@linkplain MetadataKindNode meta data kind} instance to
     * support fluent API operations
     */
    public MetadataKindNode setDescription(String description) {
        setProperty(PROPERTY_DESCRIPTION, description);
        return this;
    }

    @Override
    public String getDescription() {
        return getOptionalDescription().orElse(null);
    }

    /**
     * Returns the {@linkplain MetadataKindNode meta data kind's} description -
     * a more detailed explanation what this meta data kind represents - as an
     * {@linkplain Optional optional}.
     *
     * @return the description of the {@linkplain MetadataKindNode meta data
     * kind}
     */
    public Optional<String> getOptionalDescription() {
        return getStringProperty(PROPERTY_DESCRIPTION);
    }

    /**
     * Returns all direct {@linkplain MetadataValueNode meta data values} of
     * this {@linkplain MetadataKindNode meta data kind} as a {@linkplain Set
     * set}.
     * <p>
     * This is a comfort method for the usecases where all
     * {@linkplain MetadataValueNode meta data values} are needed. The preferred
     * way to retrieve the {@linkplain MetadataValueNode meta data values} is to
     * use the {@linkplain Stream stream} returning
     * {@linkplain #getDirectMetadataValuesStream()} method.
     *
     * @return the {@linkplain MetadataValueNode meta data values} as a
     * {@linkplain Set set}
     */
    public Set<MetadataValueNode> getDirectMetadataValues() {
        return getDirectMetadataValuesStream().collect(Collectors.toSet());
    }

    /**
     * Returns all direct {@linkplain MetadataValueNode meta data values} of
     * this {@linkplain MetadataKindNode meta data kind} as a
     * {@linkplain Stream stream}.
     *
     * @return the {@linkplain MetadataValueNode meta data values} as a
     * {@linkplain Stream stream}
     */
    public Stream<MetadataValueNode> getDirectMetadataValuesStream() {
        return getOutgoingRelationships(RelationshipTypes.HAS_METADATA_VALUE).map(
            rel -> MetadataValueNodeFactory.wrap(rel.getEndNode()));
    }

}
