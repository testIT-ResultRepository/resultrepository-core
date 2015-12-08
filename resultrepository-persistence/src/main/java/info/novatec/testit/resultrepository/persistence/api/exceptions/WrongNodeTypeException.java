package info.novatec.testit.resultrepository.persistence.api.exceptions;

import java.text.MessageFormat;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.exceptions.ResultRepositoryException;


@SuppressWarnings("serial")
public class WrongNodeTypeException extends ResultRepositoryException {

    private static final String MESSAGE = "Node {0} is not of type: {1}";

    public WrongNodeTypeException(Node nodeToBeChecked, Label shouldLabel) {
        super(MessageFormat.format(MESSAGE, nodeToBeChecked, shouldLabel));
    }

}
