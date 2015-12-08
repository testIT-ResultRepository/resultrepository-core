package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.mutable.MutableLong;
import org.junit.Test;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;


public class NodeWrapperIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final Label ANONYMOUS_LABEL = DynamicLabel.label("ANONYMOUS");
    private static final Label LABEL_ONE = DynamicLabel.label("ONE");
    private static final Label LABEL_TWO = DynamicLabel.label("TWO");

    private static final RelationshipType RELATIONSHIP_ONE = DynamicRelationshipType.withName("ONE");
    private static final RelationshipType RELATIONSHIP_TWO = DynamicRelationshipType.withName("TWO");

    private static final String PROPERTY_ONE = "property_one";
    private static final String STRING_VALUE_ONE = "value_one";
    private static final String STRING_VALUE_TWO = "value_two";
    private static final Integer INTEGER_VALUE_ONE = Integer.valueOf(1);
    private static final Integer INTEGER_VALUE_TWO = Integer.valueOf(2);
    private static final Long LONG_VALUE_ONE = Long.valueOf(1L);
    private static final Long LONG_VALUE_TWO = Long.valueOf(2L);

    /* creation of new nodes */

    @Test
    public void testCreateNode() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper = createNodeWithLabel(LABEL_ONE);
            Node expectedNode = nodeWrapper.getNode();
            Node actualNode = testGraph.getNodeById(nodeWrapper.getId());

            assertThat(actualNode).isEqualTo(expectedNode);

        });
    }

    /* wrapping of existing nodes */

    @Test
    public void testWrapExistingNode_CorrectLabel_WrappedNode() {
        inTransaction(() -> {

            Node expectedNode = testGraph.createNode(LABEL_ONE);
            AbstractNodeWrapper nodeWrapper = wrapNodeWithLabel(expectedNode, LABEL_ONE);
            Node actualNode = testGraph.getNodeById(nodeWrapper.getId());

            assertThat(actualNode).isEqualTo(expectedNode);

        });
    }

    @Test(expected = WrongNodeTypeException.class)
    public void testWrapExistingNode_WrongLabel_Exception() {
        inTransaction(() -> {
            Node node = testGraph.createNode(LABEL_ONE);
            wrapNodeWithLabel(node, LABEL_TWO);
        });
    }

    @Test(expected = NullPointerException.class)
    public void testWrapExistingNode_NullNode_Exception() {
        inTransaction(() -> {
            wrapNodeWithLabel(null, LABEL_TWO);
        });
    }

    @Test(expected = NullPointerException.class)
    public void testWrapExistingNode_NullLabel_Exception() {
        inTransaction(() -> {
            Node node = testGraph.createNode(LABEL_ONE);
            wrapNodeWithLabel(node, null);
        });
    }

    /* getNode getNodeID */

    @Test
    public void testGetNodeAndGetNodeID() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper = createAnonymousNode();

            Node expectedNode = nodeWrapper.getNode();
            Node actualNode = testGraph.getNodeById(nodeWrapper.getId());

            assertThat(actualNode).isEqualTo(expectedNode);

        });
    }

    /* setCreationTimestamp getCreationTimestamp */

    @Test
    public void testSetAndGetCreationTimestamp() {
        Date timestamp = new Date();
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setCreationTimestamp(timestamp);
            assertThat(nodeWrapper.getCreationTimestampAsDate()).isEqualTo(timestamp);
        });
    }

    /* setRequiredProperty */

    @Test(expected = IllegalArgumentException.class)
    public void testsetRequiredProperty_NullValue_Exception() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setRequiredProperty(PROPERTY_ONE, null);
        });
    }

    @Test
    public void testsetRequiredProperty_Value_Set() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setRequiredProperty(PROPERTY_ONE, STRING_VALUE_ONE);

            Object propertyValue = nodeWrapper.getNode().getProperty(PROPERTY_ONE);
            assertThat(propertyValue).isEqualTo(STRING_VALUE_ONE);

        });
    }

    /* setProperty */

    @Test
    public void testSetProperty_ToValue_ValueWasSet() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);

            Object propertyValue = nodeWrapper.getNode().getProperty(PROPERTY_ONE);
            assertThat(propertyValue).isEqualTo(STRING_VALUE_ONE);

        });
    }

    @Test
    public void testSetProperty_ToNull_ValueWasRemoved() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);
            nodeWrapper.setProperty(PROPERTY_ONE, null);

            Object propertyValue = nodeWrapper.getNode().getProperty(PROPERTY_ONE, null);
            assertThat(propertyValue).isNull();

        });
    }

    /* custom property space - getCustomProperties */

    @Test
    public void testGetCustomProperties_NoCustomPropertiesInNode_EmptySet() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            Map<String, Object> customProperties = nodeWrapper.getCustomProperties();
            assertThat(customProperties).isEmpty();
        });
    }

    @Test
    public void testGetCustomProperties_OneCustomProperty_MapWithOneEntry() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setCustomProperty("c_prop_1", 1);
            Map<String, Object> customProperties = nodeWrapper.getCustomProperties();
            assertThat(customProperties).hasSize(1);
            assertThat(customProperties).containsEntry("c_prop_1", 1);
        });
    }

    @Test
    public void testGetCustomProperties_TwoCustomProperties_MapWithTwoEntries() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setCustomProperty("c_prop_1", 1);
            nodeWrapper.setCustomProperty("c_prop_2", "hello");
            Map<String, Object> customProperties = nodeWrapper.getCustomProperties();
            assertThat(customProperties).hasSize(2);
            assertThat(customProperties).containsEntry("c_prop_1", 1);
            assertThat(customProperties).containsEntry("c_prop_2", "hello");
        });
    }

    /* custom property space - getCustomProperty */

    @Test
    public void testGetCustomProperty_PropertyNotSet_DefaultedToNull() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getCustomProperty("c_prop_1").isPresent()).isFalse();
        });
    }

    @Test
    public void testGetCustomProperty_PropertyNotSetAndDefaultGiven_DefaultReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getCustomProperty("c_prop_1", "default")).isEqualTo("default");
        });
    }

    @Test
    public void testGetCustomProperty_IntegerProperty_GotCorrectProperty() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setCustomProperty("c_prop_1", 1);
            assertThat(nodeWrapper.getCustomProperty("c_prop_1").get()).isEqualTo(1);
        });
    }

    @Test
    public void testGetCustomProperty_StringProperty_GotCorrectProperty() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setCustomProperty("c_prop_1", "hello");
            assertThat(nodeWrapper.getCustomProperty("c_prop_1").get()).isEqualTo("hello");
        });
    }

    /* custom property space */

    @Test
    public void testNamespacesOfProperties_PropertyAndCustomPropertyOfSameName_BothExistWithouCollsion() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty("property1", "hello");
            nodeWrapper.setCustomProperty("property1", "world");
            assertThat(nodeWrapper.getProperty("property1").get()).isEqualTo("hello");
            assertThat(nodeWrapper.getCustomProperty("property1").get()).isEqualTo("world");
        });
    }

    /* getProperty */

    @Test
    public void testGetProperty_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);
            assertThat(nodeWrapper.getProperty(PROPERTY_ONE).get()).isEqualTo(STRING_VALUE_ONE);
        });
    }

    @Test
    public void testGetProperty_PropertyIsNotSet_NullReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getProperty(PROPERTY_ONE).isPresent()).isFalse();
        });
    }

    @Test
    public void testGetPropertyWithDefault_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);
            assertThat(nodeWrapper.getProperty(PROPERTY_ONE, STRING_VALUE_TWO)).isEqualTo(STRING_VALUE_ONE);
        });
    }

    @Test
    public void testGetPropertyWithDefault_PropertyIsNotSet_DefaultValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getProperty(PROPERTY_ONE, STRING_VALUE_TWO)).isEqualTo(STRING_VALUE_TWO);
        });
    }

    /* getStringProperty */

    @Test
    public void testGetStringProperty_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);
            assertThat(nodeWrapper.getStringProperty(PROPERTY_ONE).get()).isEqualTo(STRING_VALUE_ONE);
        });
    }

    @Test
    public void testGetStringProperty_PropertyIsNotSet_NullReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getStringProperty(PROPERTY_ONE).isPresent()).isFalse();
        });
    }

    @Test
    public void testGetStringPropertyWithDefault_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);
            assertThat(nodeWrapper.getStringProperty(PROPERTY_ONE, STRING_VALUE_TWO)).isEqualTo(STRING_VALUE_ONE);
        });
    }

    @Test
    public void testGetStringPropertyWithDefault_PropertyIsNotSet_DefaultValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getStringProperty(PROPERTY_ONE, STRING_VALUE_TWO)).isEqualTo(STRING_VALUE_TWO);
        });
    }

    /* INTEGER PROPETIES TESTS */

    @Test
    public void testGetIntegerProperty_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, INTEGER_VALUE_ONE);
            assertThat(nodeWrapper.getIntegerProperty(PROPERTY_ONE).get()).isEqualTo(INTEGER_VALUE_ONE);
        });
    }

    @Test
    public void testGetIntegerProperty_PropertyIsNotSet_NullReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getIntegerProperty(PROPERTY_ONE).isPresent()).isFalse();
        });
    }

    @Test
    public void testGetIntegerPropertyWithDefault_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, INTEGER_VALUE_ONE);
            assertThat(nodeWrapper.getIntegerProperty(PROPERTY_ONE, INTEGER_VALUE_TWO)).isEqualTo(INTEGER_VALUE_ONE);
        });
    }

    @Test
    public void testGetIntegerPropertyWithDefault_PropertyIsNotSet_DefaultValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getIntegerProperty(PROPERTY_ONE, INTEGER_VALUE_TWO)).isEqualTo(INTEGER_VALUE_TWO);
        });
    }

    /* LONG PROPETIES TESTS */

    @Test
    public void testGetLongProperty_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, LONG_VALUE_ONE);
            assertThat(nodeWrapper.getLongProperty(PROPERTY_ONE).get()).isEqualTo(LONG_VALUE_ONE);
        });
    }

    @Test
    public void testGetLongProperty_PropertyIsNotSet_NullReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getLongProperty(PROPERTY_ONE).isPresent()).isFalse();
        });
    }

    @Test
    public void testGetLongPropertyWithDefault_PropertyIsSet_PropertyValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            nodeWrapper.setProperty(PROPERTY_ONE, LONG_VALUE_ONE);
            assertThat(nodeWrapper.getLongProperty(PROPERTY_ONE, LONG_VALUE_TWO)).isEqualTo(LONG_VALUE_ONE);
        });
    }

    @Test
    public void testGetLongPropertyWithDefault_PropertyIsNotSet_DefaultValueReturned() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper.getLongProperty(PROPERTY_ONE, LONG_VALUE_TWO)).isEqualTo(LONG_VALUE_TWO);
        });
    }

    /* deleteWithRelationships */

    @Test
    public void testDeleteWithRelationships_DeleteFirstNodeInChainOfThree_NodeAndRelationshipRemoved() {
        inTransaction(() -> {

            AbstractNodeWrapper[] nodeChain = createChainOfThreeNodes();
            nodeChain[0].deleteWithRelationships();

            // The graph should look like this:
            // nodeTwo-[two]->nodeThree
            Set<Relationship> relationshipsOfNode2 = toSet(nodeChain[1].getRelationships());
            assertThat(relationshipsOfNode2).hasSize(1);

        });
    }

    @Test
    public void testDeleteWithRelationships_DeleteSecondNodeInChainOfThree_NodeAndRelationshipsRemoved() {
        inTransaction(() -> {

            AbstractNodeWrapper[] nodeChain = createChainOfThreeNodes();
            nodeChain[1].deleteWithRelationships();

            // The graph should look like this:
            // nodeOne nodeThree

            Set<Relationship> relationshipsOfNode1 = toSet(nodeChain[0].getRelationships());
            assertThat(relationshipsOfNode1).isEmpty();

            Set<Relationship> relationshipsOfNode3 = toSet(nodeChain[2].getRelationships());
            assertThat(relationshipsOfNode3).isEmpty();

        });
    }

    private AbstractNodeWrapper[] createChainOfThreeNodes() {

        // The graph looks like this:
        // nodeOne-[one]->nodeTwo-[two]->nodeThree

        AbstractNodeWrapper[] nodeChain =
            new AbstractNodeWrapper[] { createAnonymousNode(), createAnonymousNode(), createAnonymousNode() };
        nodeChain[0].createRelationshipTo(nodeChain[1], RELATIONSHIP_ONE);
        nodeChain[1].createRelationshipTo(nodeChain[2], RELATIONSHIP_ONE);
        return nodeChain;

    }

    /* RELATIONSHIP TESTS */

    @Test
    public void testCreateUniqueRelationshipTo() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper3 = createAnonymousNode();

            Relationship relationship1 = nodeWrapper1.createUniqueRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship2 = nodeWrapper1.createUniqueRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship3 = nodeWrapper1.createUniqueRelationshipTo(nodeWrapper2, RELATIONSHIP_TWO);
            Relationship relationship4 = nodeWrapper1.createUniqueRelationshipTo(nodeWrapper3, RELATIONSHIP_ONE);

            assertThat(relationship2).isEqualTo(relationship1);
            assertThat(relationship3).isNotEqualTo(relationship1);
            assertThat(relationship4).isNotEqualTo(relationship1);

            assertThat(relationship3).isNotEqualTo(relationship2);
            assertThat(relationship4).isNotEqualTo(relationship2);

            assertThat(relationship4).isNotEqualTo(relationship3);

        });
    }

    @Test
    public void testCreateRelationshipTo() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            Relationship relationship1 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship2 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship3 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_TWO);

            assertThat(relationship2).isNotEqualTo(relationship1);
            assertThat(relationship3).isNotEqualTo(relationship1);
            assertThat(relationship3).isNotEqualTo(relationship2);

        });
    }

    @Test
    public void testDeleteAllIncomingRelationshipsOfType() {

        MutableLong id1 = new MutableLong();
        MutableLong id2 = new MutableLong();
        MutableLong id3 = new MutableLong();

        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            id1.setValue(nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE).getId());
            id2.setValue(nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE).getId());
            id3.setValue(nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_TWO).getId());

            nodeWrapper2.deleteAllIncomingRelationshipsOfType(RELATIONSHIP_ONE);

        });

        inTransaction(() -> {
            assertThat(tryToGetRelationship(id1.longValue())).isNull();
            assertThat(tryToGetRelationship(id2.longValue())).isNull();
            assertThat(tryToGetRelationship(id3.longValue())).isNotNull();
        });

    }

    @Test
    public void testDeleteAllOutgoingRelationshipsOfType() {

        MutableLong id1 = new MutableLong();
        MutableLong id2 = new MutableLong();
        MutableLong id3 = new MutableLong();

        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            id1.setValue(nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE).getId());
            id2.setValue(nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE).getId());
            id3.setValue(nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_TWO).getId());

            nodeWrapper1.deleteAllOutgoingRelationshipsOfType(RELATIONSHIP_ONE);

        });

        inTransaction(() -> {
            assertThat(tryToGetRelationship(id1.longValue())).isNull();
            assertThat(tryToGetRelationship(id2.longValue())).isNull();
            assertThat(tryToGetRelationship(id3.longValue())).isNotNull();
        });

    }

    @Test
    public void testGetSingleIncomingRelationship_NoRelationship_Null() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            assertThat(nodeWrapper1.getSingleIncomingRelationship(RELATIONSHIP_ONE).isPresent()).isFalse();
        });
    }

    @Test
    public void testGetSingleIncomingRelationship_OneRelationship_TheRelationship() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);

            assertThat(nodeWrapper2.getSingleIncomingRelationship(RELATIONSHIP_ONE)).isNotNull();

        });
    }

    @Test(expected = NotFoundException.class)
    public void testGetSingleIncomingRelationship_TwoRelationship_Exception() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);

            nodeWrapper2.getSingleIncomingRelationship(RELATIONSHIP_ONE);

        });
    }

    @Test
    public void testGetSingleOutgoingRelationship_NoRelationship_Null() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            assertThat(nodeWrapper1.getSingleOutgoingRelationship(RELATIONSHIP_ONE).isPresent()).isFalse();
        });
    }

    @Test
    public void testGetSingleOutgoingRelationship_OneRelationship_TheRelationship() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);

            assertThat(nodeWrapper1.getSingleOutgoingRelationship(RELATIONSHIP_ONE)).isNotNull();

        });
    }

    @Test(expected = NotFoundException.class)
    public void testGetSingleOutgoingRelationship_TwoRelationship_Exception() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);

            nodeWrapper1.getSingleOutgoingRelationship(RELATIONSHIP_ONE);

        });
    }

    @Test
    public void testGetRelationships() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            Relationship relationship1 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship2 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship3 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_TWO);

            Set<Relationship> relationships1 = toSet(nodeWrapper1.getRelationships(RELATIONSHIP_ONE));
            assertThat(relationships1).containsOnly(relationship1, relationship2);

            Set<Relationship> relationships2 = toSet(nodeWrapper1.getRelationships(RELATIONSHIP_TWO));
            assertThat(relationships2).containsOnly(relationship3);

        });
    }

    @Test
    public void testGetIncomingRelationships() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            Relationship relationship1 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship2 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship3 = nodeWrapper2.createRelationshipTo(nodeWrapper1, RELATIONSHIP_TWO);

            assertThat(toSet(nodeWrapper1.getIncomingRelationships(RELATIONSHIP_ONE))).isEmpty();
            assertThat(toSet(nodeWrapper1.getIncomingRelationships(RELATIONSHIP_TWO))).containsOnly(relationship3);
            assertThat(toSet(nodeWrapper2.getIncomingRelationships(RELATIONSHIP_ONE))).containsOnly(relationship1,
                relationship2);
            assertThat(toSet(nodeWrapper2.getIncomingRelationships(RELATIONSHIP_TWO))).isEmpty();

        });
    }

    @Test
    public void testGetOutgoingRelationships() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createAnonymousNode();
            AbstractNodeWrapper nodeWrapper2 = createAnonymousNode();

            Relationship relationship1 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship2 = nodeWrapper1.createRelationshipTo(nodeWrapper2, RELATIONSHIP_ONE);
            Relationship relationship3 = nodeWrapper2.createRelationshipTo(nodeWrapper1, RELATIONSHIP_TWO);

            assertThat(toSet(nodeWrapper1.getOutgoingRelationships(RELATIONSHIP_ONE))).containsOnly(relationship1,
                relationship2);
            assertThat(toSet(nodeWrapper1.getOutgoingRelationships(RELATIONSHIP_TWO))).isEmpty();
            assertThat(toSet(nodeWrapper2.getOutgoingRelationships(RELATIONSHIP_ONE))).isEmpty();
            assertThat(toSet(nodeWrapper2.getOutgoingRelationships(RELATIONSHIP_TWO))).containsOnly(relationship3);

        });
    }

    /* equals */

    @Test
    public void testEquality_EqualsWithItself_Equal() {
        inTransaction(() -> {
            AbstractNodeWrapper nodeWrapper = createAnonymousNode();
            assertThat(nodeWrapper).isEqualTo(nodeWrapper);
        });
    }

    @Test
    public void testEquality_NodeWrappersWrapTheSameNode_Equal() {
        inTransaction(() -> {

            Node node = testGraph.createNode(LABEL_ONE);

            AbstractNodeWrapper nodeWrapper1 = wrapNodeWithLabel(node, LABEL_ONE);
            AbstractNodeWrapper nodeWrapper2 = wrapNodeWithLabel(node, LABEL_ONE);

            assertThat(nodeWrapper1).isEqualTo(nodeWrapper2);

        });
    }

    @Test
    public void testEquality_NodeWrappersWrapDifferentNodes_Unequal() {
        inTransaction(() -> {

            Node node1 = testGraph.createNode(LABEL_ONE);
            Node node2 = testGraph.createNode(LABEL_TWO);

            AbstractNodeWrapper nodeWrapper1 = wrapNodeWithLabel(node1, LABEL_ONE);
            AbstractNodeWrapper nodeWrapper2 = wrapNodeWithLabel(node2, LABEL_TWO);

            assertThat(nodeWrapper1).isNotEqualTo(nodeWrapper2);

        });
    }

    /* hashCode */

    @Test
    @SuppressWarnings("boxing")
    public void testHashCode_SameNodes_SameHashcode() {
        inTransaction(() -> {

            Node node = testGraph.createNode(LABEL_ONE);

            int nodeWrapper1HashCode = wrapNodeWithLabel(node, LABEL_ONE).hashCode();
            int nodeWrapper2HashCode = wrapNodeWithLabel(node, LABEL_ONE).hashCode();

            assertThat(nodeWrapper1HashCode).isEqualTo(nodeWrapper2HashCode);

        });
    }

    @Test
    @SuppressWarnings("boxing")
    public void testHashCode_DifferentNodes_DifferentHashcode() {
        inTransaction(() -> {

            Node node1 = testGraph.createNode(LABEL_ONE);
            Node node2 = testGraph.createNode(LABEL_TWO);

            int nodeWrapper1HashCode = wrapNodeWithLabel(node1, LABEL_ONE).hashCode();
            int nodeWrapper2HashCode = wrapNodeWithLabel(node2, LABEL_TWO).hashCode();

            assertThat(nodeWrapper1HashCode).isNotEqualTo(nodeWrapper2HashCode);

        });
    }

    /* UTILITIES */

    private AbstractNodeWrapper createAnonymousNode() {
        return createNodeWithLabel(ANONYMOUS_LABEL);
    }

    private AbstractNodeWrapper createNodeWithLabel(Label label) {
        return new AbstractNodeWrapper(testGraph, label) {

            @Override
            public String toString() {
                return getNode().toString();
            }

        };
    }

    private AbstractNodeWrapper wrapNodeWithLabel(Node node, Label label) {
        return new AbstractNodeWrapper(node, label) {

            @Override
            public String toString() {
                return getNode().toString();
            }

        };
    }

    private Relationship tryToGetRelationship(long id) {
        try {
            return testGraph.getRelationshipById(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    private <T> Set<T> toSet(Stream<T> stream) {
        return stream.collect(Collectors.toSet());
    }

}
