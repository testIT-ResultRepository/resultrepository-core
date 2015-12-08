package info.novatec.testit.resultrepository.junit.exceptions;

import info.novatec.testit.resultrepository.api.exceptions.ResultRepositoryException;


/**
 * This type of exception is usually thrown as a wrapper of another exception
 * when initializing a context or service.
 */
@SuppressWarnings("serial")
public class InitializationException extends ResultRepositoryException {

    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
