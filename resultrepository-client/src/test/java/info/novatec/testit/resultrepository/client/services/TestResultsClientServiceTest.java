package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class TestResultsClientServiceTest extends AbstractClientServiceTest<TestResultsClientService> {

    @Override
    TestResultsClientService createClassUnderTest(String baseUrl) {
        return new TestResultsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        TestResultData expected = new TestResultData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTRESULT_FOR_ID, ID), expected);

        TestResultData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTRESULT_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTest() {

        TestData expected = new TestData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TEST_OF_TESTRESULT_FOR_ID, ID), expected);

        TestData found = cut.findTest(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTest_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TEST_OF_TESTRESULT_FOR_ID, ID));
        try {
            cut.findTest(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestGroupResult() {

        TestGroupResultData expected = new TestGroupResultData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTGROUPRESULT_OF_TESTRESULT_FOR_ID, ID), expected);

        TestGroupResultData found = cut.findTestGroupResult(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestGroupResult_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTGROUPRESULT_OF_TESTRESULT_FOR_ID, ID));
        try {
            cut.findTestGroupResult(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTags() {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TAGS_OF_TESTRESULT_FOR_ID, ID), Arrays.asList(tag1, tag2));

        Set<TagData> found = cut.findTags(ID);

        mockServer.verify();
        assertThat(found).containsOnly(tag1, tag2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTags_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TAGS_OF_TESTRESULT_FOR_ID, ID));
        try {
            cut.findTags(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestResultDetails() {

        TestResultDetailData detail1 = new TestResultDetailData().setId(ID);
        TestResultDetailData detail2 = new TestResultDetailData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTRESULTDETAILS_OF_TESTRESULT_FOR_ID, ID),
            Arrays.asList(detail1, detail2));

        List<TestResultDetailData> found = cut.findTestResultDetails(ID);

        mockServer.verify();
        assertThat(found).containsExactly(detail1, detail2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestResultDetails_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTRESULTDETAILS_OF_TESTRESULT_FOR_ID, ID));
        try {
            cut.findTestResultDetails(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_calculateStatistic() {

        TestResultStatistic expected = new TestResultStatistic();
        expected.setTestResultId(42L);

        setupGetForObjectExpectations(withId(V1ContextPaths.STATISTIC_OF_TESTRESULT_FOR_ID, ID), expected);

        TestResultStatistic found = cut.calculateStatistic(ID);

        mockServer.verify();
        assertThat(found).isEqualToComparingFieldByField(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_calculateStatistic_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.STATISTIC_OF_TESTRESULT_FOR_ID, ID));
        try {
            cut.calculateStatistic(ID);
        } finally {
            mockServer.verify();
        }
    }

}
