package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class TestResultDetailsClientServiceTest extends AbstractClientServiceTest<TestResultDetailsClientService> {

    @Override
    TestResultDetailsClientService createClassUnderTest(String baseUrl) {
        return new TestResultDetailsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        TestResultDetailData expected = new TestResultDetailData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTRESULTDETAIL_FOR_ID, ID), expected);

        TestResultDetailData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTRESULTDETAIL_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestResult() {

        TestResultData expected = new TestResultData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTRESULT_OF_TESTRESULTDETAIL_FOR_ID, ID), expected);

        TestResultData found = cut.findTestResult(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestResult_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTRESULT_OF_TESTRESULTDETAIL_FOR_ID, ID));
        try {
            cut.findTestResult(ID);
        } finally {
            mockServer.verify();
        }
    }

}
