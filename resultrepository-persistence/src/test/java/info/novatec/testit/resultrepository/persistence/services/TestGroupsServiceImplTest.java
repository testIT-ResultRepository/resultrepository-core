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

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TestGroupsServiceImplTest {

    private static final long ID = 42L;

    @Mock
    TestGroupNodeFactory testGroupNodeFactory;

    @InjectMocks
    TestGroupsServiceImpl cut;

    @Test
    public void testPersistenceOfDataWithCustomTimestamp() {

        TestGroupData testGroup = new TestGroupData().setName("testGroup").setCreationTimestamp(123L);

        TestGroupNode node = mock(TestGroupNode.class);
        doReturn(node).when(testGroupNodeFactory).getOrCreateFromGraph(testGroup.getName());

        cut.persist(testGroup);

        verify(node).setCreationTimestamp(123L);

    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTestGroupThrowsException() {
        doReturn(Optional.empty()).when(testGroupNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindResultsOfNonExistingTestGroupThrowsException() {
        doReturn(Optional.empty()).when(testGroupNodeFactory).getFromGraph(ID);
        cut.findTestGroupResults(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindResultsWithFilterOfNonExistingTestGroupThrowsException() {
        doReturn(Optional.empty()).when(testGroupNodeFactory).getFromGraph(ID);
        cut.findTestGroupResults(ID, result -> true);
    }

}
