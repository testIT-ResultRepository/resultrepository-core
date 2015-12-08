package info.novatec.testit.resultrepository.persistence.services;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class BuildsServiceImplTest {

    private static final long ID = 42L;

    @Mock
    BuildNodeFactory buildNodeFactory;

    @InjectMocks
    BuildsServiceImpl cut;

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingBuildThrowsException() {
        doReturn(Optional.empty()).when(buildNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindBuildJobofNonExistingBuildThrowsException() {
        doReturn(Optional.empty()).when(buildNodeFactory).getFromGraph(ID);
        cut.findBuildJob(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTagsOfNonExistingBuildThrowsException() {
        doReturn(Optional.empty()).when(buildNodeFactory).getFromGraph(ID);
        cut.findTags(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTagsWithFilterOfNonExistingBuildThrowsException() {
        doReturn(Optional.empty()).when(buildNodeFactory).getFromGraph(ID);
        cut.findTags(ID, tag -> true);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestGroupResultsOfNonExistingBuildThrowsException() {
        doReturn(Optional.empty()).when(buildNodeFactory).getFromGraph(ID);
        cut.findTestGroupResults(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestGroupResultsWithFilterOfNonExistingBuildThrowsException() {
        doReturn(Optional.empty()).when(buildNodeFactory).getFromGraph(ID);
        cut.findTestGroupResults(ID, result -> true);
    }

}
