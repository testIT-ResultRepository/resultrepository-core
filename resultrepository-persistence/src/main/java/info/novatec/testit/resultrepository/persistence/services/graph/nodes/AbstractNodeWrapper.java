package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;

import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;


/**
 * This class build the foundation for all node wrappers.
 * <p>
 * <b>Rules of node wrappers:</b>
 * </p>
 * <ol>
 * <li>All properties with a getter are either required (
 * {@linkplain #setRequiredProperty(String, Object)}) in which case the can be
 * returned as normal objects or returned as an {@linkplain Optional optional}
 * object.</li>
 * <li>All methods returning any kind of collection should also be available as
 * a {@linkplain Stream stream} returning version. Since {@linkplain Stream
 * streams} are the preferred way to interact with Neo4j's
 * {@linkplain ResourceIterable resource iterables} the collection returning
 * methods will most likely be just a call to the {@linkplain Stream stream}
 * producing method and a
 * {@linkplain Stream#collect(java.util.stream.Collector)} call.</li>
 * <li>Were possible a fluent API style is preferred. So each method which would
 * 'normally' return void should return the instance of the object it was called
 * on.</li>
 * <li>Properties which are used to identify a node uniquely should not be
 * changeable. No setters should be provided for these kinds of properties.</li>
 * </ol>
 */
public abstract class AbstractNodeWrapper {

    private static final String CUSTOM_PROPERTIES_PREFIX = "custom_";
    private static final Consumer<Relationship> DELETE_RELATIONSHIP = rel -> rel.delete();

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_TIMESTAMP_CREATION = "timestamp.creation.long";

    /* ### Graph node properties END */

    private final Node node;

    /**
     * Creates a new {@linkplain AbstractNodeWrapper} Wrapper incl. its
     * corresponding {@linkplain Node} in the given
     * {@linkplain GraphDatabaseService} with the given {@linkplain Label}.
     *
     * @param graph the {@linkplain GraphDatabaseService} to use for the
     * {@linkplain Node} creation
     * @param label the {@linkplain Label} to use for the newly created
     * {@linkplain Node}
     */
    protected AbstractNodeWrapper(GraphDatabaseService graph, Label label) {
        node = graph.createNode(label);
        setCreationTimestamp(new Date());
    }

    /**
     * Wraps the given {@linkplain Node} as a {@linkplain AbstractNodeWrapper}
     * with the given {@linkplain Label}.
     *
     * @param node the {@linkplain Node} to wrap
     * @param label the {@linkplain Label} to use
     */
    protected AbstractNodeWrapper(Node node, Label label) {
        assertThatNodeHasCorrectLabel(node, label);
        this.node = node;
    }

    private void assertThatNodeHasCorrectLabel(Node nodeToBeChecked, Label shouldLabel) {
        if (!nodeToBeChecked.hasLabel(shouldLabel)) {
            throw new WrongNodeTypeException(nodeToBeChecked, shouldLabel);
        }
    }

    // CUSTOM PROPERTIES

    public Map<String, Object> getCustomProperties() {
        return stream(getNode().getPropertyKeys()).filter(key -> isCustomPropertyKey(key))
            .collect(Collectors.toMap(key -> removeCustomKeyPrefix(key), key -> getProperty(key, null)));
    }

    private Boolean isCustomPropertyKey(String key) {
        return key.startsWith(CUSTOM_PROPERTIES_PREFIX);
    }

    private String removeCustomKeyPrefix(String key) {
        return StringUtils.removeStart(key, CUSTOM_PROPERTIES_PREFIX);
    }

    public Optional<Object> getCustomProperty(String key) {
        return Optional.ofNullable(getCustomProperty(key, null));
    }

    public Object getCustomProperty(String key, Object defaultValue) {
        return getProperty(addCustomKeyPrefix(key), defaultValue);
    }

    public void setCustomProperty(String key, Object value) {
        setProperty(addCustomKeyPrefix(key), value);
    }

    private String addCustomKeyPrefix(String key) {
        return CUSTOM_PROPERTIES_PREFIX + key;
    }

    // ACCESS

    protected Node getNode() {
        return node;
    }

    public GraphDatabaseService getGraphDatabase() {
        return getNode().getGraphDatabase();
    }

    public Long getId() {
        return getNode().getId();
    }

    public void setCreationTimestamp(Date timestamp) {
        setCreationTimestamp(timestamp.getTime());
    }

    public void setCreationTimestamp(long timestamp) {
        setProperty(PROPERTY_TIMESTAMP_CREATION, timestamp);
    }

    public Date getCreationTimestampAsDate() {
        return new Date(getCreationTimestamp());
    }

    public Long getCreationTimestamp() {
        return ( Long ) getProperty(PROPERTY_TIMESTAMP_CREATION, null);
    }

    // PROPERTIES

    protected void setRequiredProperty(String key, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(MessageFormat.format("value of {} must not be null!", key));
        }
        setProperty(key, value);
    }

    protected void setProperty(String key, Object value) {
        if (value != null) {
            getNode().setProperty(key, value);
        } else {
            getNode().removeProperty(key);
        }
    }

    protected Optional<Object> getProperty(String key) {
        return Optional.ofNullable(getNullDefaultedProperty(key));
    }

    protected Object getProperty(String key, Object defaultValue) {
        return getNode().getProperty(key, defaultValue);
    }

    protected Optional<String> getStringProperty(String key) {
        return Optional.ofNullable(( String ) getNullDefaultedProperty(key));
    }

    protected String getStringProperty(String key, String defaultValue) {
        return ( String ) getProperty(key, defaultValue);
    }

    protected Optional<Integer> getIntegerProperty(String key) {
        return Optional.ofNullable(( Integer ) getNullDefaultedProperty(key));
    }

    protected Integer getIntegerProperty(String key, Integer defaultValue) {
        return ( Integer ) getProperty(key, defaultValue);
    }

    protected Optional<Long> getLongProperty(String key) {
        return Optional.ofNullable(( Long ) getNullDefaultedProperty(key));
    }

    protected Long getLongProperty(String key, Long defaultValue) {
        return ( Long ) getProperty(key, defaultValue);
    }

    private Object getNullDefaultedProperty(String key) {
        return getProperty(key, null);
    }

    // UTILITIES

    /**
     * Deletes this node including all of its incoming and outgoing
     * relationships. After calling this method the Node is invalid and must not
     * be used further!
     */
    public final void deleteWithRelationships() {
        deleteRelationships();
        node.delete();
    }

    private void deleteRelationships() {
        stream(node.getRelationships()).forEach(relationship -> relationship.delete());
    }

    public Relationship createUniqueRelationshipTo(AbstractNodeWrapper nodeWrapper, RelationshipType relationshipType) {

        Predicate<Relationship> relationshipsToNodeWrapper = rel -> nodeWrapper.getNode().equals(rel.getEndNode());
        Optional<Relationship> firstRelationship =
            getRelationships(relationshipType).filter(relationshipsToNodeWrapper).findFirst();

        return firstRelationship.isPresent() ? firstRelationship.get() : createRelationshipTo(nodeWrapper, relationshipType);

    }

    public Relationship createRelationshipTo(AbstractNodeWrapper nodeWrapper, RelationshipType relationshipType) {
        return getNode().createRelationshipTo(nodeWrapper.getNode(), relationshipType);
    }

    protected void deleteAllIncomingRelationshipsOfType(RelationshipType relationshipType) {
        getIncomingRelationships(relationshipType).forEach(DELETE_RELATIONSHIP);
    }

    protected Stream<Relationship> getIncomingRelationships(RelationshipType... relationshipTypes) {
        return stream(getNode().getRelationships(Direction.INCOMING, relationshipTypes));
    }

    protected void deleteAllOutgoingRelationshipsOfType(RelationshipType relationshipType) {
        getOutgoingRelationships(relationshipType).forEach(DELETE_RELATIONSHIP);
    }

    protected Stream<Relationship> getOutgoingRelationships(RelationshipType... relationshipTypes) {
        return stream(getNode().getRelationships(Direction.OUTGOING, relationshipTypes));
    }

    protected Optional<Relationship> getSingleIncomingRelationship(RelationshipType relationshipType) {
        return getSingleRelationship(relationshipType, Direction.INCOMING);
    }

    protected Optional<Relationship> getSingleOutgoingRelationship(RelationshipType relationshipType) {
        return getSingleRelationship(relationshipType, Direction.OUTGOING);
    }

    private Optional<Relationship> getSingleRelationship(RelationshipType relationshipType, Direction direction) {
        return Optional.ofNullable(getNode().getSingleRelationship(relationshipType, direction));
    }

    protected Stream<Relationship> getRelationships() {
        return stream(getNode().getRelationships());
    }

    protected Stream<Relationship> getRelationships(RelationshipType... relationshipTypes) {
        return stream(getNode().getRelationships(relationshipTypes));
    }

    private <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + node.hashCode();
        return result;
    }

    @Override
    public final boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        AbstractNodeWrapper other = ( AbstractNodeWrapper ) obj;
        return node.equals(other.node);

    }

}
