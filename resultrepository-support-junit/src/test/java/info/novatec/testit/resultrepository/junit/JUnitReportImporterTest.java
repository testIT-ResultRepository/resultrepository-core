package info.novatec.testit.resultrepository.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.client.services.ImportClientService;
import info.novatec.testit.resultrepository.junit.report.JUnitReportReader;


@RunWith(MockitoJUnitRunner.class)
public class JUnitReportImporterTest {

    static final String BASE_REPORT_FOLDER = "src/test/resources/junit_report_importer";
    static final String TEST_NAME_1 = "tests.JUnitReportImporterTest#testSuccessfullTestcase";
    static final String TEST_NAME_2 = "tests.JUnitReportImporterTest#testSkippedTestcase";
    static final String TEST_NAME_3 = "tests.JUnitReportImporterTest#testFailedTestcase";
    static final String TEST_NAME_4 = "tests.JUnitReportImporterTest#testErrorTestcase";

    static final BuildData BUILD =
        new BuildData().setBuildJob(new BuildJobData().setName("JUnitReportImporter")).setBuildNumber(1);

    @Mock
    ImportClientService service;
    @Spy
    JUnitReportReader reader;

    @Captor
    ArgumentCaptor<TestGroupResultData> resultCaptor;

    @InjectMocks
    JUnitReportImporter cut;

    @Test(expected = IllegalArgumentException.class)
    public void testImportJUnitReportsFromFolder_NotAFolder() {
        File testsuiteFolder = new File("notAFolder");
        cut.importJUnitReportsFromFolder(testsuiteFolder, BUILD);
    }

    @Test
    public void testImportJUnitReportsFromFolder() {

        File testsuiteFolder = new File(BASE_REPORT_FOLDER);
        cut.importJUnitReportsFromFolder(testsuiteFolder, BUILD);

        verify(service, times(2)).importResult(resultCaptor.capture());

    }

    @Test
    public void testImportJUnitReportsFromFolderRecursively() {

        File testsuiteFolder = new File(BASE_REPORT_FOLDER + "/recursion");
        cut.importJUnitReportsFromFolderRecursively(testsuiteFolder, BUILD);

        verify(service, times(6)).importResult(resultCaptor.capture());

    }

    @Test
    public void testImportSingleJUnitReports() {

        File reportFile = new File(BASE_REPORT_FOLDER + "/TEST-testsuite1.xml");
        cut.importJUnitReportFromFile(reportFile, BUILD);

        verify(service).importResult(resultCaptor.capture());
        assertTestGroupResult(resultCaptor.getValue());

    }

    /* utilities */

    private void assertTestGroupResult(TestGroupResultData testplanResult) {

        assertThat(testplanResult.getBuild().getBuildJob().getName()).isEqualTo("JUnitReportImporter");
        assertThat(testplanResult.getBuild().getBuildNumber()).isEqualTo(1);
        assertThat(testplanResult.getTestGroup().getName()).isEqualTo("tests.JUnitReportImporterTest");

        Set<TestResultData> testResults = testplanResult.getTestResults();
        assertThat(testResults.size()).isEqualTo(4);
        assertTestResult1(getTestResultForName(testResults, TEST_NAME_1));
        assertTestResult2(getTestResultForName(testResults, TEST_NAME_2));
        assertTestResult3(getTestResultForName(testResults, TEST_NAME_3));
        assertTestResult4(getTestResultForName(testResults, TEST_NAME_4));

    }

    private TestResultData getTestResultForName(Set<TestResultData> testResults, String name) {
        for (TestResultData result : testResults) {
            if (result.getTest().getName().equals(name)) {
                return result;
            }
        }
        throw new IllegalStateException("No test result found for name: " + name);
    }

    private void assertTestResult1(TestResultData testResult) {

        assertThat(testResult.getDuration()).isEqualTo(500L);
        assertThat(testResult.getStatus()).isEqualTo(ResultStatus.PASSED);
        assertThat(testResult.getTest().getName()).isEqualTo(TEST_NAME_1);

        assertThat(testResult.getTestResultDetails().size()).isEqualTo(1);
        assertThat(testResult.getTestResultDetails().get(0).getMessage()).isEqualTo("passed");
        assertThat(testResult.getTestResultDetails().get(0).getType()).isEqualTo(DetailType.SUCCESS);

    }

    private void assertTestResult2(TestResultData testResult) {

        assertThat(testResult.getDuration()).isEqualTo(0L);
        assertThat(testResult.getStatus()).isEqualTo(ResultStatus.SKIPPED);
        assertThat(testResult.getTest().getName()).isEqualTo(TEST_NAME_2);

        assertThat(testResult.getTestResultDetails().size()).isEqualTo(1);
        assertThat(testResult.getTestResultDetails().get(0).getMessage()).isEqualTo("skipped");
        assertThat(testResult.getTestResultDetails().get(0).getType()).isEqualTo(DetailType.INFORMATION);

    }

    private void assertTestResult3(TestResultData testResult) {

        assertThat(testResult.getDuration()).isEqualTo(1000L);
        assertThat(testResult.getStatus()).isEqualTo(ResultStatus.FAILED);
        assertThat(testResult.getTest().getName()).isEqualTo(TEST_NAME_3);

        assertThat(testResult.getTestResultDetails().size()).isEqualTo(2);
        assertThat(testResult.getTestResultDetails().get(0).getMessage()).isEqualTo("failureMessage");
        assertThat(testResult.getTestResultDetails().get(0).getType()).isEqualTo(DetailType.ERROR);
        assertThat(testResult.getTestResultDetails().get(1).getMessage()).isEqualTo("stacktracedata");
        assertThat(testResult.getTestResultDetails().get(1).getType()).isEqualTo(DetailType.INFORMATION);

    }

    private void assertTestResult4(TestResultData testResult) {

        assertThat(testResult.getDuration()).isEqualTo(250L);
        assertThat(testResult.getStatus()).isEqualTo(ResultStatus.EXCEPTION);
        assertThat(testResult.getTest().getName()).isEqualTo(TEST_NAME_4);

        assertThat(testResult.getTestResultDetails().size()).isEqualTo(2);
        assertThat(testResult.getTestResultDetails().get(0).getMessage()).isEqualTo("errorMessage");
        assertThat(testResult.getTestResultDetails().get(0).getType()).isEqualTo(DetailType.EXCEPTION);
        assertThat(testResult.getTestResultDetails().get(1).getMessage()).isEqualTo("stacktracedata");
        assertThat(testResult.getTestResultDetails().get(1).getType()).isEqualTo(DetailType.INFORMATION);

    }

}
