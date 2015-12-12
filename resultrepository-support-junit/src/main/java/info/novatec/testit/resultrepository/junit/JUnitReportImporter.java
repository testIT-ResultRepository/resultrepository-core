package info.novatec.testit.resultrepository.junit;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.junit.exceptions.ImportException;
import info.novatec.testit.resultrepository.junit.report.JUnitReportReader;
import info.novatec.testit.resultrepository.junit.report.JUnitReportUtils;
import info.novatec.testit.resultrepository.junit.report.xml.Testsuite;
import info.novatec.testit.resultrepository.remote.v1.ImportRemoteService;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Imports standard XML based JUnit report the result repository.
 */
public class JUnitReportImporter {

    private JUnitReportReader reportReader;
    private ImportRemoteService importService;
    private JUnitReportConverter converter = new JUnitReportConverter();

    public JUnitReportImporter(JUnitReportReader reportReader, ImportRemoteService importService) {
        this.reportReader = reportReader;
        this.importService = importService;
    }

    /**
     * Imports all JUnit reports from a given directory into the repository
     * using the optional {@link BuildData build} information as a reference
     * point. The build might be <code>null</code> if the results should not be
     * linked to any particular build. Sub-directories are not included when
     * looking for report files!
     *
     * @param reportsFolder the directory which should be searched for report
     * files
     * @param build the optional build
     * @throws ImportException in case anything goes wrong when parsing or
     * importing the results
     */
    public void importJUnitReportsFromFolder(File reportsFolder, BuildData build) throws ImportException {
        for (File jUnitReportFile : getJUnitReportFilesFromFolder(reportsFolder)) {
            importJUnitReportFromFile(jUnitReportFile, build);
        }
    }

    private File[] getJUnitReportFilesFromFolder(File reportsFolder) {
        if (!reportsFolder.isDirectory()) {
            throw new IllegalArgumentException("Not a folder: " + reportsFolder);
        }
        return reportsFolder.listFiles(JUnitReportUtils.REPORT_FILE_FILTER);
    }

    /**
     * Imports all JUnit reports from a given directory into the repository
     * using the optional {@link BuildData build} information as a reference
     * point. The build might be <code>null</code> if the results should not be
     * linked to any particular build. Sub-directories are included when looking
     * for report files!
     *
     * @param rootFolder the directory which should be recursively searched for
     * report files
     * @param build the optional build
     * @throws ImportException in case anything goes wrong when parsing or
     * importing the results
     */
    public void importJUnitReportsFromFolderRecursively(File rootFolder, BuildData build) throws ImportException {
        for (File jUnitReportFile : getJUnitReportFilesFromFolderRecursively(rootFolder)) {
            importJUnitReportFromFile(jUnitReportFile, build);
        }
    }

    private List<File> getJUnitReportFilesFromFolderRecursively(File parentFolder) {

        List<File> returnValue = new LinkedList<File>();

        File[] reportFiles = parentFolder.listFiles(JUnitReportUtils.REPORT_FILE_FILTER);
        if (reportFiles != null) {
            for (File reportFile : reportFiles) {
                returnValue.add(reportFile);
            }
        }

        File[] directories = parentFolder.listFiles(JUnitReportUtils.DIRECTORY_FILTER);
        if (directories != null) {
            for (File folder : directories) {
                returnValue.addAll(getJUnitReportFilesFromFolderRecursively(folder));
            }
        }

        return returnValue;

    }

    /**
     * Imports a JUnit report from a given file into the repository using the
     * optional {@link BuildData build} information as a reference point. The
     * build might be <code>null</code> if the results should not be linked to
     * any particular build.
     *
     * @param reportFile the report file to import
     * @param build the optional build
     * @throws ImportException in case anything goes wrong when parsing or
     * importing the results
     */
    public void importJUnitReportFromFile(File reportFile, BuildData build) throws ImportException {

        Testsuite testsuite = readJUnitReportFromFile(reportFile);

        long timestamp = reportFile.lastModified();

        TestGroupResultData testGroupResult = converter.convertTestsuiteToTestGroupResult(testsuite, timestamp);
        if (build != null) {
            testGroupResult.setBuild(build);
        }

        importJUnitReportData(reportFile, testGroupResult);

    }

    private Testsuite readJUnitReportFromFile(File reportFile) throws ImportException {
        try {
            return reportReader.readFrom(reportFile);
        } catch (JAXBException e) {
            throw new ImportException("error while parsing report file: " + reportFile, e);
        }
    }

    private void importJUnitReportData(File reportFile, TestGroupResultData testplanResult) throws ImportException {
        try {
            importService.importResult(testplanResult);
        } catch (RemoteOperationException e) {
            throw new ImportException("error while importing report file: " + reportFile, e);
        }
    }

    public void setReportReader(JUnitReportReader reportReader) {
        this.reportReader = reportReader;
    }

    public void setImportService(ImportRemoteService importService) {
        this.importService = importService;
    }

}
