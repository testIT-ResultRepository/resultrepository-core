package info.novatec.testit.resultrepository.api.exceptions;

import java.text.MessageFormat;


@SuppressWarnings("serial")
public class IllegalMetadataFormatException extends ResultRepositoryException {

    public static final String MESSAGE = "Illegal format for '{0}'. RegEx: {1}";

    public IllegalMetadataFormatException(String offendingString, String pattern) {
        super(MessageFormat.format(MESSAGE, offendingString, pattern));
    }

}
