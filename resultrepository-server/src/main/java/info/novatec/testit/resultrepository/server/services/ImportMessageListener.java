package info.novatec.testit.resultrepository.server.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.metrics.api.metrics.CounterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.Metrics;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;
import info.novatec.testit.resultrepository.server.api.ImportService;


@Component
public class ImportMessageListener implements SessionAwareMessageListener<Message> {

    protected static final CounterMetric TOTAL_UNABLE_TO_IMPORT =
        Metrics.counterMetric(ImportMessageListener.class, "totalUnableToImport");

    @Autowired
    private RetryTemplate retryTemplate;
    @Autowired
    private ImportService importService;
    @Autowired
    private MetricsManager metrics;

    @Override
    @Measured
    @Logged(logLevel = LogLevel.TRACE)
    public void onMessage(Message message, Session session) throws JMSException {

        TestGroupResultData result = message.getBody(TestGroupResultData.class);

        retryTemplate.execute(context -> {

            /* This should be the only action inside the callback since it
             * persists data. It should be the only reason the retry mechanism
             * is triggered! */

            importService.importResult(result);
            return null;

        }, context -> {
            metrics.increment(TOTAL_UNABLE_TO_IMPORT);
            throw ( RuntimeException ) context.getLastThrowable();
        });

    }

}
