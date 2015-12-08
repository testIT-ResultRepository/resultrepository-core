package info.novatec.testit.resultrepository.api.exceptions;

/**
 * This is the base exception type for all exceptions thrown by the
 * ResultRepository.
 * <p>
 * This class is intended to be sub-classed and cannot be initialized directly!
 */
@SuppressWarnings("serial")
public class ResultRepositoryException extends RuntimeException {

    protected ResultRepositoryException(String message) {
        super(message);
    }

    protected ResultRepositoryException(Throwable cause) {
        super(cause);
    }

    protected ResultRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

}
