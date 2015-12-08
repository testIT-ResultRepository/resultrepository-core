package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class TestGroupsClientServiceTest extends AbstractClientServiceTest<TestGroupsClientService> {

    @Override
    TestGroupsClientService createClassUnderTest(String baseUrl) {
        return new TestGroupsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_persist() {

        TestGroupData testGroup = new TestGroupData().setName("testGroup");
        TestGroupData expected =
            new TestGroupData().setName("testGroup").setId(ID).setCreationTimestamp(System.currentTimeMillis());
        setupPostForObjectExpectations(V1ContextPaths.TESTGROUPS, testGroup, expected);

        TestGroupData persisted = cut.persist(testGroup);

        mockServer.verify();
        assertThat(persisted).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_persist_exception() {
        TestGroupData testGroup = new TestGroupData().setName("testGroup");
        setupPostExceptionExpectations(V1ContextPaths.TESTGROUPS, testGroup);
        try {
            cut.persist(testGroup);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findAll() {

        TestGroupData testGroup1 = new TestGroupData().setId(ID);
        TestGroupData testGroup2 = new TestGroupData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(V1ContextPaths.TESTGROUPS, Arrays.asList(testGroup1, testGroup2));

        Set<TestGroupData> found = cut.findAll();

        mockServer.verify();
        assertThat(found).containsOnly(testGroup1, testGroup2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findAll_exception() {
        setupGetExceptionExpectations(V1ContextPaths.TESTGROUPS);
        try {
            cut.findAll();
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        TestGroupData expected = new TestGroupData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTGROUP_FOR_ID, ID), expected);

        TestGroupData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTGROUP_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestGroupResults() {

        TestGroupResultData result1 = new TestGroupResultData().setId(ID);
        TestGroupResultData result2 = new TestGroupResultData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTGROUPRESULTS_OF_TESTGROUP_FOR_ID, ID),
            Arrays.asList(result1, result2));

        Set<TestGroupResultData> found = cut.findTestGroupResults(ID);

        mockServer.verify();
        assertThat(found).containsOnly(result1, result2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestGroupResults_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTGROUPRESULTS_OF_TESTGROUP_FOR_ID, ID));
        try {
            cut.findTestGroupResults(ID);
        } finally {
            mockServer.verify();
        }
    }

}
