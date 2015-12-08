package info.novatec.testit.resultrepository.persistence.api.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;


public class WrongNodeTypeExceptionTest {

    @Test
    public void testMessageFormat() {

        Node nodeToBeChecked = mock(Node.class);
        doReturn("[node]").when(nodeToBeChecked).toString();

        Label shouldLabel = mock(Label.class);
        doReturn("[label]").when(shouldLabel).toString();

        WrongNodeTypeException cut = new WrongNodeTypeException(nodeToBeChecked, shouldLabel);

        assertThat(cut.getMessage()).isEqualTo("Node [node] is not of type: [label]");

    }

}
