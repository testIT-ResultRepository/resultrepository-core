package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.MultipleFoundException;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class NodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final Label LABEL_ONE = DynamicLabel.label("ONE");
    private static final Label LABEL_TWO = DynamicLabel.label("TWO");

    private static final String PROPERTY_ONE = "property_one";
    private static final String STRING_VALUE_ONE = "value_one";
    private static final String STRING_VALUE_TWO = "value_two";

    private AbstractNodeFactory cut;

    @Before
    public void setUp() {
        cut = new AbstractNodeFactory() {
            // test instance
        };
        cut.setGraphDatabase(testGraph);
    }

    /* STATIC METHOD TESTS */

    @Test
    public void testGetSingleNodeFromIndex_NodeExists_TheNode() {
        inTransaction(() -> {

            AbstractNodeWrapper wrapper = createNodeWithLabel(LABEL_ONE);
            wrapper.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);

            Node node = cut.getSingleNodeFromIndex(LABEL_ONE, PROPERTY_ONE, STRING_VALUE_ONE).get();
            assertThat(node).isEqualTo(wrapper.getNode());

        });
    }

    @Test
    public void testGetSingleNodeFromIndex_NodeDoesntExist_Null() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createNodeWithLabel(LABEL_ONE);
            nodeWrapper1.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);

            Optional<Node> node = cut.getSingleNodeFromIndex(LABEL_ONE, PROPERTY_ONE, STRING_VALUE_TWO);
            assertThat(node.isPresent()).isFalse();

        });
    }

    @Test(expected = MultipleFoundException.class)
    public void testGetSingleNodeFromIndex_MoreThenOneNode_Exception() {
        inTransaction(() -> {

            AbstractNodeWrapper nodeWrapper1 = createNodeWithLabel(LABEL_ONE);
            nodeWrapper1.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);

            AbstractNodeWrapper nodeWrapper2 = createNodeWithLabel(LABEL_ONE);
            nodeWrapper2.setProperty(PROPERTY_ONE, STRING_VALUE_ONE);

            // should return two hits
            cut.getSingleNodeFromIndex(LABEL_ONE, PROPERTY_ONE, STRING_VALUE_ONE);

        });
    }

    @Test
    public void testGetNodesForLabel() {
        inTransaction(() -> {

            Node node1 = createNodeWithLabel(LABEL_ONE).getNode();
            Node node2 = createNodeWithLabel(LABEL_ONE).getNode();
            Node node3 = createNodeWithLabel(LABEL_TWO).getNode();

            Set<Node> nodesWithLabelOne = toSet(cut.getNodesForLabel(LABEL_ONE));
            assertThat(nodesWithLabelOne).containsOnly(node1, node2);

            Set<Node> nodesWithLabelTwo = toSet(cut.getNodesForLabel(LABEL_TWO));
            assertThat(nodesWithLabelTwo).containsOnly(node3);

        });
    }

    /* UTILITIES */

    private AbstractNodeWrapper createNodeWithLabel(Label label) {
        return new AbstractNodeWrapper(testGraph, label) {

            @Override
            public String toString() {
                return getNode().toString();
            }

        };
    }

    private <T> Set<T> toSet(Stream<T> stream) {
        return stream.collect(Collectors.toSet());
    }

}
