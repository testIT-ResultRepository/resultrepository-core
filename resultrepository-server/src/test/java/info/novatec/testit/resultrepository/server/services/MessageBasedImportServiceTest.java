package info.novatec.testit.resultrepository.server.services;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;
import info.novatec.testit.resultrepository.server.settings.ImportSettings;


@RunWith(MockitoJUnitRunner.class)
public class MessageBasedImportServiceTest {

    private static final String QUEUE_NAME = "queueName";

    @Spy
    ImportSettings importTestGroupResultSettings;

    @Mock
    MetricsManager metrics;
    @Mock
    JmsTemplate jmsTemplate;

    @InjectMocks
    MessageBasedImportService cut;

    @Before
    public void setUp() {
        importTestGroupResultSettings.setQueueName(QUEUE_NAME);
    }

    @Test
    public void testImportOfTestGroupResult() {
        TestGroupResultData result = new TestGroupResultData();
        cut.importResult(result);
        verify(jmsTemplate).convertAndSend(eq(QUEUE_NAME), same(result));
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testThatMetricsAreRecordedForImport() {
        TestGroupResultData result = new TestGroupResultData();
        cut.importResult(result);
        verify(metrics).increment(MessageBasedImportService.TOTAL_SEND);
        verifyNoMoreInteractions(metrics);
    }

}
