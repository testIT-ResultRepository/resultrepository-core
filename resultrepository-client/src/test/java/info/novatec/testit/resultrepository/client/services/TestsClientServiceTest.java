package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class TestsClientServiceTest extends AbstractClientServiceTest<TestsClientService> {

    @Override
    TestsClientService createClassUnderTest(String baseUrl) {
        return new TestsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_persist() {

        TestData test = new TestData().setName("test");
        TestData expected = new TestData().setName("test").setId(ID).setCreationTimestamp(System.currentTimeMillis());
        setupPostForObjectExpectations(V1ContextPaths.TESTS, test, expected);

        TestData persisted = cut.persist(test);

        mockServer.verify();
        assertThat(persisted).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_persist_exception() {
        TestData test = new TestData().setName("test");
        setupPostExceptionExpectations(V1ContextPaths.TESTS, test);
        try {
            cut.persist(test);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findAll() {

        TestData test1 = new TestData().setId(ID);
        TestData test2 = new TestData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(V1ContextPaths.TESTS, Arrays.asList(test1, test2));

        Set<TestData> found = cut.findAll();

        mockServer.verify();
        assertThat(found).containsOnly(test1, test2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findAll_exception() {
        setupGetExceptionExpectations(V1ContextPaths.TESTS);
        try {
            cut.findAll();
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        TestData expected = new TestData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TEST_FOR_ID, ID), expected);

        TestData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TEST_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestResults() {

        TestResultData result1 = new TestResultData().setId(ID);
        TestResultData result2 = new TestResultData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTRESULTS_OF_TEST_FOR_ID, ID),
            Arrays.asList(result1, result2));

        Set<TestResultData> found = cut.findTestResults(ID);

        mockServer.verify();
        assertThat(found).containsOnly(result1, result2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestResults_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTRESULTS_OF_TEST_FOR_ID, ID));
        try {
            cut.findTestResults(ID);
        } finally {
            mockServer.verify();
        }
    }

}
