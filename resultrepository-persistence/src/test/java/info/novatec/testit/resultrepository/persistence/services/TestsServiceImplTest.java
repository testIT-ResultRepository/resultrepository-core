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

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TestsServiceImplTest {

    private static final long ID = 42L;

    @Mock
    TestNodeFactory testNodeFactory;

    @InjectMocks
    TestsServiceImpl cut;

    @Test
    public void testPersistenceOfDataWithCustomTimestamp() {

        TestData test = new TestData().setName("test").setCreationTimestamp(123L);

        TestNode node = mock(TestNode.class);
        doReturn(node).when(testNodeFactory).getOrCreateFromGraph(test.getName());

        cut.persist(test);

        verify(node).setCreationTimestamp(123L);

    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTestThrowsException() {
        doReturn(Optional.empty()).when(testNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindResultsOfNonExistingTestThrowsException() {
        doReturn(Optional.empty()).when(testNodeFactory).getFromGraph(ID);
        cut.findTestResults(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindResultsWithFilterOfNonExistingTestThrowsException() {
        doReturn(Optional.empty()).when(testNodeFactory).getFromGraph(ID);
        cut.findTestResults(ID, result -> true);
    }

}
