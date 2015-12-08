package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class TestGroupResultsClientServiceTest extends AbstractClientServiceTest<TestGroupResultsClientService> {

    @Override
    TestGroupResultsClientService createClassUnderTest(String baseUrl) {
        return new TestGroupResultsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        TestGroupResultData expected = new TestGroupResultData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTGROUPRESULT_FOR_ID, ID), expected);

        TestGroupResultData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTGROUPRESULT_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestGroup() {

        TestGroupData expected = new TestGroupData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTGROUP_OF_TESTGROUPRESULT_FOR_ID, ID), expected);

        TestGroupData found = cut.findTestGroup(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestGroup_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTGROUP_OF_TESTGROUPRESULT_FOR_ID, ID));
        try {
            cut.findTestGroup(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findBuild() {

        BuildData expected = new BuildData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.BUILD_OF_TESTGROUPRESULT_FOR_ID, ID), expected);

        BuildData found = cut.findBuild(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findBuild_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.BUILD_OF_TESTGROUPRESULT_FOR_ID, ID));
        try {
            cut.findBuild(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTags() {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TAGS_OF_TESTGROUPRESULT_FOR_ID, ID), Arrays.asList(tag1, tag2));

        Set<TagData> found = cut.findTags(ID);

        mockServer.verify();
        assertThat(found).containsOnly(tag1, tag2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTags_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TAGS_OF_TESTGROUPRESULT_FOR_ID, ID));
        try {
            cut.findTags(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestResults() {

        TestResultData result1 = new TestResultData().setId(ID);
        TestResultData result2 = new TestResultData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTRESULTS_OF_TESTGROUPRESULT_FOR_ID, ID),
            Arrays.asList(result1, result2));

        Set<TestResultData> found = cut.findTestResults(ID);

        mockServer.verify();
        assertThat(found).containsOnly(result1, result2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestResults_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTRESULTS_OF_TESTGROUPRESULT_FOR_ID, ID));
        try {
            cut.findTestResults(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_calculateStatistic() {

        TestGroupResultStatistic expected = new TestGroupResultStatistic();
        expected.setTestGroupResultId(42L);

        setupGetForObjectExpectations(withId(V1ContextPaths.STATISTIC_OF_TESTGROUPRESULT_FOR_ID, ID), expected);

        TestGroupResultStatistic found = cut.calculateStatistic(ID);

        mockServer.verify();
        assertThat(found).isEqualToComparingFieldByField(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_calculateStatistic_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.STATISTIC_OF_TESTGROUPRESULT_FOR_ID, ID));
        try {
            cut.calculateStatistic(ID);
        } finally {
            mockServer.verify();
        }
    }

}
