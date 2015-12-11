package info.novatec.testit.resultrepository.junit;

import static info.novatec.testit.resultrepository.junit.report.JUnitReportUtils.convertReportTimeToMillis;

import java.util.LinkedList;
import java.util.List;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.junit.report.xml.Testcase;
import info.novatec.testit.resultrepository.junit.report.xml.Testsuite;


public class JUnitReportConverter {

    public TestGroupResultData convertTestsuiteToTestGroupResult(Testsuite testsuite, long timestamp) {

        TestGroupData testGroup = new TestGroupData();
        testGroup.setName(testsuite.getName());

        TestGroupResultData testplanResult = new TestGroupResultData();
        testplanResult.setCreationTimestamp(timestamp);
        testplanResult.setTestGroup(testGroup);

        for (Testcase testcase : testsuite.getTestcases()) {
            TestResultData testResult = convertTestcaseToTestResult(testcase, timestamp);
            testplanResult.addTestResult(testResult);
        }

        return testplanResult;

    }

    public TestResultData convertTestcaseToTestResult(Testcase testcase, long timestamp) {

        TestData test = new TestData();
        test.setName(getTestNameFromTestcase(testcase));

        TestResultData testResult = new TestResultData();
        testResult.setCreationTimestamp(timestamp);
        testResult.setTest(test);
        testResult.setDuration(convertReportTimeToMillis(testcase.getTime()));
        testResult.setStatus(getResultStatusFromTestcase(testcase));
        testResult.addTestResultDetails(getTestResultDetailsFromTestcase(testcase, timestamp));
        return testResult;

    }

    private static String getTestNameFromTestcase(Testcase testcase) {
        return testcase.getClassname() + "#" + testcase.getName();
    }

    private static ResultStatus getResultStatusFromTestcase(Testcase testcase) {

        if (testcase.getSkipped() != null) {
            return ResultStatus.SKIPPED;
        }

        if (testcase.getFailure() != null) {
            return ResultStatus.FAILED;
        }

        if (testcase.getError() != null) {
            return ResultStatus.EXCEPTION;
        }

        return ResultStatus.PASSED;

    }

    public static List<TestResultDetailData> getTestResultDetailsFromTestcase(Testcase testcase, long timestamp) {

        List<TestResultDetailData> entries = new LinkedList<TestResultDetailData>();

        if (testcase.getSkipped() != null) {
            entries.add(createEntry(DetailType.INFORMATION, "skipped", timestamp));
        } else if (testcase.getFailure() != null) {
            entries.add(createEntry(DetailType.ERROR, testcase.getFailure().getMessage(), timestamp));
            entries.add(createEntry(DetailType.INFORMATION, testcase.getFailure().getStacktrace(), timestamp));
        } else if (testcase.getError() != null) {
            entries.add(createEntry(DetailType.EXCEPTION, testcase.getError().getMessage(), timestamp));
            entries.add(createEntry(DetailType.INFORMATION, testcase.getError().getStacktrace(), timestamp));
        } else {
            entries.add(createEntry(DetailType.SUCCESS, "passed", timestamp));
        }

        return entries;

    }

    private static TestResultDetailData createEntry(DetailType type, String message, long timestamp) {
        TestResultDetailData entry = new TestResultDetailData();
        entry.setCreationTimestamp(timestamp);
        entry.setType(type);
        entry.setMessage(message);
        return entry;
    }

}
