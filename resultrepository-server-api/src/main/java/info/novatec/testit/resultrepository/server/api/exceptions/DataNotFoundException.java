package info.novatec.testit.resultrepository.server.api.exceptions;

import java.text.MessageFormat;
import java.util.Arrays;

import info.novatec.testit.resultrepository.api.exceptions.ResultRepositoryException;


@SuppressWarnings("serial")
public class DataNotFoundException extends ResultRepositoryException {

    private static final String MESSAGE_ID = "No data of type {0} was found for ID: {1}";
    private static final String MESSAGE_VALUES = "No data of type {0} was found for: {1}";

    public DataNotFoundException(Class<?> dataType, long id) {
        super(MessageFormat.format(MESSAGE_ID, dataType.getSimpleName(), id));
    }

    public DataNotFoundException(Class<?> dataType, String... value) {
        super(MessageFormat.format(MESSAGE_VALUES, dataType.getSimpleName(), Arrays.toString(value)));
    }

}
