package info.novatec.testit.resultrepository.client.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.descriptors.TagDescriptor;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


public class TagsClientServiceTest extends AbstractClientServiceTest<TagsClientService> {

    @Override
    TagsClientService createClassUnderTest(String baseUrl) {
        return new TagsClientService(baseUrl);
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_persist() {

        TagData test = new TagData().setValue("tag");
        TagData expected = new TagData().setValue("tag").setId(ID).setCreationTimestamp(System.currentTimeMillis());
        setupPostForObjectExpectations(V1ContextPaths.TAGS, test, expected);

        TagData persisted = cut.persist(test);

        mockServer.verify();
        assertThat(persisted).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_persist_exception() {
        TagData test = new TagData().setValue("tag");
        setupPostExceptionExpectations(V1ContextPaths.TAGS, test);
        try {
            cut.persist(test);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findAll() {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(V1ContextPaths.TAGS, Arrays.asList(tag1, tag2));

        Set<TagData> found = cut.findAll();

        mockServer.verify();
        assertThat(found).containsOnly(tag1, tag2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findAll_exception() {
        setupGetExceptionExpectations(V1ContextPaths.TAGS);
        try {
            cut.findAll();
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findMatching() {

        TagDescriptor descriptor = new TagDescriptor().setPattern("fooBar").usingEquality();

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        setupPostForObjectExpectations(V1ContextPaths.TAGS_SEARCH, descriptor, Arrays.asList(tag1, tag2));

        Set<TagData> found = cut.findMatching(descriptor);

        mockServer.verify();
        assertThat(found).containsOnly(tag1, tag2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findMatching_exception() {
        TagDescriptor descriptor = new TagDescriptor().setPattern("fooBar").usingEquality();
        setupPostExceptionExpectations(V1ContextPaths.TAGS_SEARCH, descriptor);
        try {
            cut.findMatching(descriptor);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findById() {

        TagData expected = new TagData().setId(ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TAG_FOR_ID, ID), expected);

        TagData found = cut.findById(ID);

        mockServer.verify();
        assertThat(found).isEqualTo(expected);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findById_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TAG_FOR_ID, ID));
        try {
            cut.findById(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findBuilds() {

        BuildData build1 = new BuildData().setId(ID);
        BuildData build2 = new BuildData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.BUILDS_OF_TAG_FOR_ID, ID), Arrays.asList(build1, build2));

        Set<BuildData> found = cut.findBuilds(ID);

        mockServer.verify();
        assertThat(found).containsOnly(build1, build2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findBuilds_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.BUILDS_OF_TAG_FOR_ID, ID));
        try {
            cut.findBuilds(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestGroupResults() {

        TestGroupResultData result1 = new TestGroupResultData().setId(ID);
        TestGroupResultData result2 = new TestGroupResultData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTGROUPRESULTS_OF_TAG_FOR_ID, ID),
            Arrays.asList(result1, result2));

        Set<TestGroupResultData> found = cut.findTestGroupResults(ID);

        mockServer.verify();
        assertThat(found).containsOnly(result1, result2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestGroupResults_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTGROUPRESULTS_OF_TAG_FOR_ID, ID));
        try {
            cut.findTestGroupResults(ID);
        } finally {
            mockServer.verify();
        }
    }

    @Test
    public void testThatRESTServiceIsCalledProperly_findTestResults() {

        TestResultData result1 = new TestResultData().setId(ID);
        TestResultData result2 = new TestResultData().setId(ANOTHER_ID);
        setupGetForObjectExpectations(withId(V1ContextPaths.TESTRESULTS_OF_TAG_FOR_ID, ID), Arrays.asList(result1, result2));

        Set<TestResultData> found = cut.findTestResults(ID);

        mockServer.verify();
        assertThat(found).containsOnly(result1, result2);

    }

    @Test(expected = RemoteOperationException.class)
    public void testThatRESTServiceIsCalledProperly_findTestResults_exception() {
        setupGetExceptionExpectations(withId(V1ContextPaths.TESTRESULTS_OF_TAG_FOR_ID, ID));
        try {
            cut.findTestResults(ID);
        } finally {
            mockServer.verify();
        }
    }

}
