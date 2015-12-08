package info.novatec.testit.resultrepository.persistence.services;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TestResultsServiceImplTest {

    private static final long ID = 42L;

    @Mock
    TestResultNodeFactory testResultNodeFactory;

    @InjectMocks
    TestResultsServiceImpl cut;

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTestResultThrowsException() {
        doReturn(Optional.empty()).when(testResultNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestOfNonExistingTestResultThrowsException() {
        doReturn(Optional.empty()).when(testResultNodeFactory).getFromGraph(ID);
        cut.findTest(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestGroupResultOfNonExistingTestResultThrowsException() {
        doReturn(Optional.empty()).when(testResultNodeFactory).getFromGraph(ID);
        cut.findTestGroupResult(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTagsOfNonExistingTestResultThrowsException() {
        doReturn(Optional.empty()).when(testResultNodeFactory).getFromGraph(ID);
        cut.findTags(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTagsWithFilterOfNonExistingTestResultThrowsException() {
        doReturn(Optional.empty()).when(testResultNodeFactory).getFromGraph(ID);
        cut.findTags(ID, tag -> true);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindDetailsOfNonExistingTestResultThrowsException() {
        doReturn(Optional.empty()).when(testResultNodeFactory).getFromGraph(ID);
        cut.findTestResultDetails(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindDetailsWithFilterOfNonExistingTestResultThrowsException() {
        doReturn(Optional.empty()).when(testResultNodeFactory).getFromGraph(ID);
        cut.findTestResultDetails(ID, detail -> true);
    }

}
