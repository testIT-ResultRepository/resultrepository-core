package info.novatec.testit.resultrepository.remote.v1.exceptions;

import info.novatec.testit.resultrepository.api.exceptions.ResultRepositoryException;


@SuppressWarnings("serial")
public class RemoteOperationException extends ResultRepositoryException {

    public RemoteOperationException(Throwable cause) {
        super("remote operation failed: " + cause.getMessage(), cause);
    }

}
