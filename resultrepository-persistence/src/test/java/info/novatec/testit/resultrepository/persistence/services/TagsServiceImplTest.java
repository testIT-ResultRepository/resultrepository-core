package info.novatec.testit.resultrepository.persistence.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TagsServiceImplTest {

    private static final long ID = 42L;
    private static final String VALUE = "value";

    @Mock
    TagNodeFactory tagNodeFactory;

    @InjectMocks
    TagsServiceImpl cut;

    @Test
    public void testPersistenceOfDataWithCustomTimestamp() {

        TagData tag = new TagData().setValue("tag").setCreationTimestamp(123L);

        TagNode node = mock(TagNode.class);
        doReturn(node).when(tagNodeFactory).getOrCreateFromGraph(tag.getValue());

        cut.persist(tag);

        verify(node).setCreationTimestamp(123L);

    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTagByIdThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTagByValueThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(VALUE);
        cut.findByValue(VALUE);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindBuildsOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(ID);
        cut.findBuilds(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindBuildsWithFilterOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(ID);
        cut.findBuilds(ID, build -> true);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestGroupResultsOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(ID);
        cut.findTestGroupResults(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestGroupResultsWithFilterOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(ID);
        cut.findTestGroupResults(ID, result -> true);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestResultsOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(ID);
        cut.findTestResults(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestResultsWithFilterOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(tagNodeFactory).getFromGraph(ID);
        cut.findTestResults(ID, result -> true);
    }

}
