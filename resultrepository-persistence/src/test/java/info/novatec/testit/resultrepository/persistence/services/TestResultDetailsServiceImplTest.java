package info.novatec.testit.resultrepository.persistence.services;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TestResultDetailsServiceImplTest {

    private static final long ID = 42L;

    @Mock
    TestResultDetailNodeFactory testResultDetailNodeFactory;

    @InjectMocks
    TestResultDetailsServiceImpl cut;

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTestResultDetailThrowsException() {
        doReturn(Optional.empty()).when(testResultDetailNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindTestResultOfNonExistingTestResultDetailThrowsException() {
        doReturn(Optional.empty()).when(testResultDetailNodeFactory).getFromGraph(ID);
        cut.findTestResult(ID);
    }

}
