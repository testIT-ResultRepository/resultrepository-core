package info.novatec.testit.resultrepository.server.services;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;
import info.novatec.testit.resultrepository.server.api.ImportService;


@RunWith(MockitoJUnitRunner.class)
public class ImportMessageListenerTest {

    static final int MAX_RETRY = 3;

    @Mock
    MetricsManager metrics;
    @Spy
    RetryTemplate retryTemplate;
    @Mock
    ImportService importService;

    @Mock
    Message message;

    @InjectMocks
    ImportMessageListener cut;

    TestGroupResultData result;

    @Before
    public void setUp() throws JMSException {

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(MAX_RETRY);
        retryTemplate.setRetryPolicy(retryPolicy);

        BackOffPolicy backOffPolicy = new NoBackOffPolicy();
        retryTemplate.setBackOffPolicy(backOffPolicy);

        result = new TestGroupResultData();
        doReturn(result).when(message).getBody(TestGroupResultData.class);

    }

    @Test
    public void testThatMessagesContainingTestGroupResultDataAreProcessed() throws JMSException {
        cut.onMessage(message, null);
        verify(importService).importResult(result);
        verifyNoMoreInteractions(importService);
    }

    @Test(expected = RuntimeException.class)
    public void testThatRetriesAreExecutedUponExceptionInImport() throws JMSException {
        doThrow(RuntimeException.class).when(importService).importResult(result);
        try {
            cut.onMessage(message, null);
        } finally {
            verify(importService, times(MAX_RETRY)).importResult(result);
            verifyNoMoreInteractions(importService);
        }
    }

    @Test
    public void testThatSuccessOnRetryDoesNotThrowException() throws JMSException {

        stubServiceToThrowExceptionsUntilTheLastRetryAttempt();

        cut.onMessage(message, null);

        verify(importService, times(MAX_RETRY)).importResult(result);
        verifyNoMoreInteractions(importService);

    }

    @Test
    public void testThatNoMetricsAreRecordedIfTheImportWasSucessfull() throws JMSException {
        cut.onMessage(message, null);
        verifyZeroInteractions(metrics);
    }

    @Test(expected = RuntimeException.class)
    public void testThatMetricsAreRecordedIfTheImportFailed() throws JMSException {
        doThrow(RuntimeException.class).when(importService).importResult(result);
        try {
            cut.onMessage(message, null);
        } finally {
            verify(metrics).increment(ImportMessageListener.TOTAL_UNABLE_TO_IMPORT);
            verifyNoMoreInteractions(metrics);
        }
    }

    private void stubServiceToThrowExceptionsUntilTheLastRetryAttempt() {
        MutableInt counter = new MutableInt();
        doAnswer(invocation -> {
            counter.increment();
            if (counter.intValue() < MAX_RETRY) {
                throw new RuntimeException();
            }
            return null;
        }).when(importService).importResult(result);
    }
}
