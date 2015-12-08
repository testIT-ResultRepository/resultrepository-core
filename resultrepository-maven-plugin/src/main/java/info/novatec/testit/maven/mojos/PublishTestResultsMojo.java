package info.novatec.testit.maven.mojos;

import static java.lang.String.format;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.client.services.ImportClientService;
import info.novatec.testit.resultrepository.junit.JUnitReportImporter;
import info.novatec.testit.resultrepository.junit.report.JUnitReportReader;
import info.novatec.testit.resultrepository.junit.report.JUnitReportUtils;


@Mojo(name = "publishTestResults", requiresDirectInvocation = true)
public class PublishTestResultsMojo extends AbstractMojo {

    private static final String EXECUTE_START_MESSAGE = "Publishing test results to testIT ResultRepository:";
    private static final String EXECUTE_END_MESSAGE = "...finished!";
    private static final String EXCEPTION_MESSAGE = "Exception while publishing test results to ResultRepository!";

    private static final String SUREFIRE_HEADLINE = "Exporting Surefire reports from: %s";
    private static final String NO_SUREFIRE_REPORTS_FOUND = "No Surefire reports found.";
    private static final String SUREFIRE_REPORTS_FOLDER = "surefire-reports";

    private static final String FAILSAFE_HEADLINE = "Exporting Failsafe reports from: %s";
    private static final String NO_FAILSAFE_REPORTS_FOUND = "No Failsafe reports found.";
    private static final String FAILSAFE_REPORTS_FOLDER = "failsafe-reports";

    private static final String EXPORTED_FILE = "\t exported: %s";
    private static final String TARGET_FOLDER = "target";

    @Component
    private MavenProject project;

    @Parameter(property = "resultrepository.url", required = true)
    private String resultRepositoryURL;

    @Parameter(property = "build.jobname", required = true)
    private String buildJobName;

    @Parameter(property = "build.number", required = true)
    private int buildNumber;

    /* variables */

    private JUnitReportImporter importer;

    /* implementation */

    @Override
    public void execute() throws MojoExecutionException {
        info(EXECUTE_START_MESSAGE);
        tryToExecuteExportOfXMLResults();
        info(EXECUTE_END_MESSAGE);

    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private void tryToExecuteExportOfXMLResults() {
        try {
            executeExportOfXMLResults();
        } catch (Exception e) {
            getLog().warn(EXCEPTION_MESSAGE, e);
        }
    }

    private void executeExportOfXMLResults() {
        prepareExportToResultRepository();
        exportSurefireResults();
        exportFailsafeResults();
    }

    private void prepareExportToResultRepository() {
        JUnitReportReader reportReader = new JUnitReportReader();
        ImportClientService importService = new ImportClientService(resultRepositoryURL);
        importer = new JUnitReportImporter(reportReader, importService);
    }

    private void exportSurefireResults() {
        File surefireReportFolder = getSurefireReportsFolder();
        if (surefireReportFolder.isDirectory()) {
            info(format(SUREFIRE_HEADLINE, surefireReportFolder));
            importResultsFromFolderIntoResultRepository(surefireReportFolder);
        } else {
            info(NO_SUREFIRE_REPORTS_FOUND);
        }
    }

    private File getSurefireReportsFolder() {
        return new File(getTargetFolder(), SUREFIRE_REPORTS_FOLDER);
    }

    private void exportFailsafeResults() {
        File failsafeReportFolder = getFailsafeReportsFolder();
        if (failsafeReportFolder.isDirectory()) {
            info(format(FAILSAFE_HEADLINE, failsafeReportFolder));
            importResultsFromFolderIntoResultRepository(failsafeReportFolder);
        } else {
            info(NO_FAILSAFE_REPORTS_FOUND);
        }
    }

    private File getFailsafeReportsFolder() {
        return new File(getTargetFolder(), FAILSAFE_REPORTS_FOLDER);
    }

    private void importResultsFromFolderIntoResultRepository(File reportsFolder) {

        BuildJobData buildJob = new BuildJobData().setName(buildJobName);
        BuildData buildData = new BuildData().setBuildJob(buildJob).setBuildNumber(buildNumber);

        for (File jUnitReportFile : getJUnitReportFilesFromFolder(reportsFolder)) {
            importer.importJUnitReportFromFile(jUnitReportFile, buildData);
            info(format(EXPORTED_FILE, jUnitReportFile.getName()));
        }

    }

    private File[] getJUnitReportFilesFromFolder(File reportsFolder) {
        return reportsFolder.listFiles(JUnitReportUtils.REPORT_FILE_FILTER);
    }

    private File getTargetFolder() {
        return new File(project.getBasedir(), TARGET_FOLDER);
    }

    private void info(Object message) {
        getLog().info(String.valueOf(message));
    }

}
