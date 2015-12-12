package info.novatec.testit.resultrepository.plugin.junit.exceptions;

import info.novatec.testit.resultrepository.api.exceptions.ResultRepositoryException;


@SuppressWarnings("serial")
public class JUnitReportImportException extends ResultRepositoryException {

    public JUnitReportImportException(String message) {
        super(message);
    }

}
