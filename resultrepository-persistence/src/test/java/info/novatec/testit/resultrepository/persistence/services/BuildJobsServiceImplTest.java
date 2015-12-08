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

import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildJobNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildJobNodeFactory;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class BuildJobsServiceImplTest {

    private static final long ID = 42L;

    @Mock
    BuildJobNodeFactory buildJobNodeFactory;

    @InjectMocks
    BuildJobsServiceImpl cut;

    @Test
    public void testPersistenceOfDataWithCustomTimestamp() {

        BuildJobData buildJob = new BuildJobData().setName("buildJob").setCreationTimestamp(123L);

        BuildJobNode node = mock(BuildJobNode.class);
        doReturn(node).when(buildJobNodeFactory).getOrCreateFromGraph(buildJob.getName());

        cut.persist(buildJob);

        verify(node).setCreationTimestamp(123L);

    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(buildJobNodeFactory).getFromGraph(ID);
        cut.findById(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindBuildsOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(buildJobNodeFactory).getFromGraph(ID);
        cut.findBuilds(ID);
    }

    @Test(expected = DataNotFoundException.class)
    public void testThatTryingToFindBuildsWithFilterOfNonExistingTagThrowsException() {
        doReturn(Optional.empty()).when(buildJobNodeFactory).getFromGraph(ID);
        cut.findBuilds(ID, build -> true);
    }

}
