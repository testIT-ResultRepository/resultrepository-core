package info.novatec.testit.resultrepository.junit.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import info.novatec.testit.resultrepository.junit.report.xml.Property;
import info.novatec.testit.resultrepository.junit.report.xml.Testcase;
import info.novatec.testit.resultrepository.junit.report.xml.Testsuite;


public class JUnitReportReaderTest {

    private static final String REPORT_FOLDER = "src/test/resources/junit_report_reader_test_data/";

    private JUnitReportReader cut = new JUnitReportReader();

    @Test
    public void testReadTestsuite_SuiteWithoutProperties_PropertiesEmpty() throws JAXBException {
        Testsuite testsuite = loadTestsuiteFromTestresource("noTestcases.xml");
        assertThat(testsuite.getProperties()).isNull();
    }

    @Test
    public void testReadTestsuite_SuiteWithProperties_PropertiesReadCorrectly() throws JAXBException {

        Testsuite testsuite = loadTestsuiteFromTestresource("properties.xml");

        List<Property> propertyList = testsuite.getProperties().getPropertyList();
        assertThat(propertyList.size()).isEqualTo(2);
        assertThat(propertyList.get(0).getName()).isEqualTo("property_1");
        assertThat(propertyList.get(0).getValue()).isEqualTo("value_1");
        assertThat(propertyList.get(1).getName()).isEqualTo("property_2");
        assertThat(propertyList.get(1).getValue()).isEqualTo("value_2");

    }

    @Test
    public void testReadTestsuite_SuiteWithoutTestcases_SuiteDataRead() throws JAXBException {

        Testsuite testsuite = loadTestsuiteFromTestresource("noTestcases.xml");

        assertThat(testsuite.getName()).isEqualTo("tests.OneTests");
        assertThat(testsuite.getTests()).isEqualTo(0);
        assertThat(testsuite.getSkipped()).isEqualTo(0);
        assertThat(testsuite.getFailures()).isEqualTo(0);
        assertThat(testsuite.getErrors()).isEqualTo(0);
        assertThat(testsuite.getTime()).isEqualTo("0.000");

    }

    @Test
    public void testReadTestsuite_SuiteWithOneTestcase() throws JAXBException {

        Testsuite testsuite = loadTestsuiteFromTestresource("oneTestcase.xml");

        assertThat(testsuite.getTestcases().size()).isEqualTo(1);

        Testcase testcase = testsuite.getTestcases().get(0);
        assertThat(testcase.getName()).isEqualTo("testMethodOne");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("0.123");
        assertThat(testcase.getError()).isNull();
        assertThat(testcase.getFailure()).isNull();
        assertThat(testcase.getSkipped()).isNull();

    }

    @Test
    public void testReadTestsuite_SuiteWithTwoTestcases() throws JAXBException {

        Testsuite testsuite = loadTestsuiteFromTestresource("twoTestcases.xml");

        assertThat(testsuite.getTestcases().size()).isEqualTo(2);

        Testcase testcase = testsuite.getTestcases().get(0);
        assertThat(testcase.getName()).isEqualTo("testMethodOne");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("0.123");
        assertThat(testcase.getError()).isNull();
        assertThat(testcase.getFailure()).isNull();
        assertThat(testcase.getSkipped()).isNull();

        testcase = testsuite.getTestcases().get(1);
        assertThat(testcase.getName()).isEqualTo("testMethodTwo");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("1.000");
        assertThat(testcase.getError()).isNull();
        assertThat(testcase.getFailure()).isNull();
        assertThat(testcase.getSkipped()).isNull();

    }

    @Test
    public void testReadTestsuite_SuiteWithTwoErrorTestcases() throws JAXBException {

        Testsuite testsuite = loadTestsuiteFromTestresource("withErrors.xml");

        assertThat(testsuite.getTestcases().size()).isEqualTo(2);

        Testcase testcase = testsuite.getTestcases().get(0);
        assertThat(testcase.getName()).isEqualTo("testMethodTwo");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("1.000");
        assertThat(testcase.getFailure()).isNull();
        assertThat(testcase.getSkipped()).isNull();

        assertThat(testcase.getError()).isNotNull();
        assertThat(testcase.getError().getMessage()).isEqualTo("errorMessage");
        assertThat(testcase.getError().getType()).isEqualTo("java.lang.AssertionError");
        assertThat(testcase.getError().getStacktrace()).isEqualTo("stacktracedata");

        testcase = testsuite.getTestcases().get(1);
        assertThat(testcase.getName()).isEqualTo("testMethodThree");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("1.000");
        assertThat(testcase.getFailure()).isNull();
        assertThat(testcase.getSkipped()).isNull();

        assertThat(testcase.getError()).isNotNull();
        assertThat(testcase.getError().getMessage()).isNull();
        assertThat(testcase.getError().getType()).isEqualTo("java.lang.AssertionError");
        assertThat(testcase.getError().getStacktrace()).isEqualTo("");

    }

    @Test
    public void testReadTestsuite_SuiteWithTwoFailedTestcases() throws JAXBException {

        Testsuite testsuite = loadTestsuiteFromTestresource("withFailures.xml");

        assertThat(testsuite.getTestcases().size()).isEqualTo(2);

        Testcase testcase = testsuite.getTestcases().get(0);
        assertThat(testcase.getName()).isEqualTo("testMethodTwo");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("1.000");
        assertThat(testcase.getError()).isNull();
        assertThat(testcase.getSkipped()).isNull();

        assertThat(testcase.getFailure()).isNotNull();
        assertThat(testcase.getFailure().getMessage()).isEqualTo("errorMessage");
        assertThat(testcase.getFailure().getType()).isEqualTo("java.lang.AssertionError");
        assertThat(testcase.getFailure().getStacktrace()).isEqualTo("stacktracedata");

        testcase = testsuite.getTestcases().get(1);
        assertThat(testcase.getName()).isEqualTo("testMethodThree");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("1.000");
        assertThat(testcase.getError()).isNull();
        assertThat(testcase.getSkipped()).isNull();

        assertThat(testcase.getFailure()).isNotNull();
        assertThat(testcase.getFailure().getMessage()).isNull();
        assertThat(testcase.getFailure().getType()).isEqualTo("java.lang.AssertionError");
        assertThat(testcase.getFailure().getStacktrace()).isEqualTo("");

    }

    @Test
    public void testReadTestsuite_SuiteWithTwoSkippedTestcases() throws JAXBException {

        Testsuite testsuite = loadTestsuiteFromTestresource("withSkipped.xml");

        assertThat(testsuite.getTestcases().size()).isEqualTo(2);

        Testcase testcase = testsuite.getTestcases().get(0);
        assertThat(testcase.getName()).isEqualTo("testMethodTwo");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("0");
        assertThat(testcase.getError()).isNull();
        assertThat(testcase.getFailure()).isNull();
        assertThat(testcase.getSkipped()).isNotNull();

        testcase = testsuite.getTestcases().get(1);
        assertThat(testcase.getName()).isEqualTo("testMethodThree");
        assertThat(testcase.getClassname()).isEqualTo("tests.OneTests");
        assertThat(testcase.getTime()).isEqualTo("0");
        assertThat(testcase.getError()).isNull();
        assertThat(testcase.getFailure()).isNull();
        assertThat(testcase.getSkipped()).isNotNull();

    }

    @Test(expected = JAXBException.class)
    public void testReadTestsuite_MissingNameAttribute_Exception() throws JAXBException {
        loadTestsuiteFromTestresource("negative/missingTestsuiteAttribute_name.xml");
    }

    @Test(expected = JAXBException.class)
    public void testReadTestsuite_MissingTimeAttribute_Exception() throws JAXBException {
        loadTestsuiteFromTestresource("negative/missingTestsuiteAttribute_time.xml");
    }

    @Test(expected = JAXBException.class)
    public void testReadTestsuite_MissingTestsAttribute_Exception() throws JAXBException {
        loadTestsuiteFromTestresource("negative/missingTestsuiteAttribute_tests.xml");
    }

    @Test(expected = JAXBException.class)
    public void testReadTestsuite_MissingErrorsAttribute_Exception() throws JAXBException {
        loadTestsuiteFromTestresource("negative/missingTestsuiteAttribute_errors.xml");
    }

    @Test(expected = JAXBException.class)
    public void testReadTestsuite_MissingFailuresAttribute_Exception() throws JAXBException {
        loadTestsuiteFromTestresource("negative/missingTestsuiteAttribute_failures.xml");
    }

    @Test(expected = JAXBException.class)
    public void testReadTestsuite_MissingSkippedAttribute_Exception() throws JAXBException {
        loadTestsuiteFromTestresource("negative/missingTestsuiteAttribute_skipped.xml");
    }

    /* utilities */

    private Testsuite loadTestsuiteFromTestresource(String testReportFileName) throws JAXBException {
        File reportFile = new File(REPORT_FOLDER + testReportFileName);
        return cut.readFrom(reportFile);
    }

}
