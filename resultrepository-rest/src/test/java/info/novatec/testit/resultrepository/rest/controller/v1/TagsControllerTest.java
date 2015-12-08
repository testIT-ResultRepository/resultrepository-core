package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.BUILDS_OF_TAG_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TAGS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TAGS_SEARCH;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TAG_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTGROUPRESULTS_OF_TAG_FOR_ID;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TESTRESULTS_OF_TAG_FOR_ID;
import static org.mockito.Mockito.doReturn;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.remote.v1.descriptors.TagDescriptor;
import info.novatec.testit.resultrepository.server.api.TagsService;


@RunWith(MockitoJUnitRunner.class)
public class TagsControllerTest extends AbstractControllerTest {

    static final String VALUE = "value";

    @Mock
    TagsService delegateService;

    @InjectMocks
    TagsController cut;

    @Override
    Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_persist() throws Exception {

        TagData tag = new TagData().setValue(VALUE);
        TagData persistedTag = new TagData(tag).setId(ID);
        doReturn(persistedTag).when(delegateService).persist(tag);

        performAndAssertPost(TAGS, tag, persistedTag);

    }

    @Test
    public void testController_findAllTags() throws Exception {

        TagData tag1 = new TagData().setId(ID);
        TagData tag2 = new TagData().setId(ANOTHER_ID);
        Set<TagData> tags = Sets.newSet(tag1, tag2);
        doReturn(tags).when(delegateService).findAll();

        performAndAssertGet(TAGS, tags);

    }

    @Test
    public void testController_findTagsMatching_EqualityDescriptor() throws Exception {

        TagDescriptor descriptor = new TagDescriptor(VALUE).usingEquality();
        TagData tag = new TagData().setId(ID).setValue(VALUE);

        Set<TagData> tags = Sets.newSet(tag);
        doReturn(tag).when(delegateService).findByValue(VALUE);

        performAndAssertPost(TAGS_SEARCH, descriptor, tags);

    }

    @Test
    public void testController_findTagsMatching_PatternDescriptor() throws Exception {

        TagDescriptor descriptor = new TagDescriptor("foo.*");
        TagData tag1 = new TagData().setId(ID).setValue("foo");
        TagData tag2 = new TagData().setId(ID).setValue("fooBar");
        TagData tag3 = new TagData().setId(ID).setValue("bar");

        Set<TagData> allTags = Sets.newSet(tag1, tag2, tag3);
        Set<TagData> expectedTags = Sets.newSet(tag1, tag2);
        doReturn(allTags).when(delegateService).findAll();

        performAndAssertPost(TAGS_SEARCH, descriptor, expectedTags);

    }

    @Test
    public void testController_findTagById() throws Exception {

        TagData tag = new TagData().setId(ID);
        doReturn(tag).when(delegateService).findById(ID);

        performAndAssertGet(withId(TAG_FOR_ID, ID), tag);

    }

    @Test
    public void testController_findBuildsForTagWithId() throws Exception {

        BuildData build1 = new BuildData().setId(ID);
        BuildData build2 = new BuildData().setId(ANOTHER_ID);
        Set<BuildData> builds = Sets.newSet(build1, build2);
        doReturn(builds).when(delegateService).findBuilds(ID);

        performAndAssertGet(withId(BUILDS_OF_TAG_FOR_ID, ID), builds);

    }

    @Test
    public void testController_findTestGroupResultsForTagWithId() throws Exception {

        TestGroupResultData result1 = new TestGroupResultData().setId(ID);
        TestGroupResultData result2 = new TestGroupResultData().setId(ANOTHER_ID);
        Set<TestGroupResultData> results = Sets.newSet(result1, result2);
        doReturn(results).when(delegateService).findTestGroupResults(ID);

        performAndAssertGet(withId(TESTGROUPRESULTS_OF_TAG_FOR_ID, ID), results);

    }

    @Test
    public void testController_findTestResultsForTagWithId() throws Exception {

        TestResultData result1 = new TestResultData().setId(ID);
        TestResultData result2 = new TestResultData().setId(ANOTHER_ID);
        Set<TestResultData> results = Sets.newSet(result1, result2);
        doReturn(results).when(delegateService).findTestResults(ID);

        performAndAssertGet(withId(TESTRESULTS_OF_TAG_FOR_ID, ID), results);

    }

}
