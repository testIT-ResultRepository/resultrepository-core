package info.novatec.testit.resultrepository.persistence.aspects;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.aspects.TransactedAspectIntegrationTest.TransactedAspectTestConfiguration;
import info.novatec.testit.resultrepository.persistence.config.PersistenceConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { PersistenceConfiguration.AspectConfiguration.class,
    TransactedAspectTestConfiguration.class })
public class TransactedAspectIntegrationTest {

    public static class TransactedAspectTestConfiguration {

        @Bean
        public GraphDatabaseService graph() {
            return mock(GraphDatabaseService.class);
        }

        @Bean
        public TestService service() {
            return new TestService();
        }

    }

    public static class TestService {

        @Transacted
        public void execute() {
            // do nothing
        }

        @Transacted
        public void executeWithException() {
            throw new RuntimeException("test exception");
        }

    }

    @Autowired
    private GraphDatabaseService graph;

    @Autowired
    private TestService service;

    @Test
    public void testThatTransactionIsCreatedCommittedAndClosedIfThereAreNoExceptions() {

        Transaction tx = mock(Transaction.class);
        doReturn(tx).when(graph).beginTx();

        service.execute();

        verify(tx).success();
        verify(tx).close();
        verifyNoMoreInteractions(tx);

    }

    @Test(expected = RuntimeException.class)
    public void testThatTransactionIsRolledBackInCaseOfExceptions() {

        Transaction tx = mock(Transaction.class);
        doReturn(tx).when(graph).beginTx();

        try {
            service.executeWithException();
        } finally {
            verify(tx).close();
            verifyNoMoreInteractions(tx);
        }

    }

}
