package info.novatec.testit.resultrepository.junit.exceptions;

import info.novatec.testit.resultrepository.api.exceptions.ResultRepositoryException;


/**
 * This type of exception is usually thrown as a wrapper of another exception
 * when importing data.
 */
@SuppressWarnings("serial")
public class ImportException extends ResultRepositoryException {

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }

}
