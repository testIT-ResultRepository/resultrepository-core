package info.novatec.testit.resultrepository.persistence.services;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TestGroupResultsServiceImplTest {

    private static final long ID = 42L;

    @Mock
    TestGroupResultNodeFactory testGroupResultNodeFactory;

    @InjectMocks
    TestGroupResultsServiceImpl cut;

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTestGroupResultThrowsException() {
        doReturn(Optional.empty()).when(testGroupResultNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestGroupOfNonExistingTestGroupResultThrowsException() {
        doReturn(Optional.empty()).when(testGroupResultNodeFactory).getFromGraph(ID);
        cut.findTestGroup(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindBuildOfNonExistingTestGroupResultThrowsException() {
        doReturn(Optional.empty()).when(testGroupResultNodeFactory).getFromGraph(ID);
        cut.findBuild(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTagsOfNonExistingTestGroupResultThrowsException() {
        doReturn(Optional.empty()).when(testGroupResultNodeFactory).getFromGraph(ID);
        cut.findTags(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTagsWithFilterOfNonExistingTestGroupResultThrowsException() {
        doReturn(Optional.empty()).when(testGroupResultNodeFactory).getFromGraph(ID);
        cut.findTags(ID, tag -> true);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestResultsOfNonExistingTestGroupResultThrowsException() {
        doReturn(Optional.empty()).when(testGroupResultNodeFactory).getFromGraph(ID);
        cut.findTestResults(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestResultsWithFilterOfNonExistingTestGroupResultThrowsException() {
        doReturn(Optional.empty()).when(testGroupResultNodeFactory).getFromGraph(ID);
        cut.findTestResults(ID, testResult -> true);
    }

}
