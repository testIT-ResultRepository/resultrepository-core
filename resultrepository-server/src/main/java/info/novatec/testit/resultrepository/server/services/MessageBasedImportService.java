package info.novatec.testit.resultrepository.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.metrics.api.metrics.CounterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.Metrics;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;
import info.novatec.testit.resultrepository.server.api.AsynchronousImportService;
import info.novatec.testit.resultrepository.server.settings.ImportSettings;


@Service
public class MessageBasedImportService implements AsynchronousImportService {

    protected static final CounterMetric TOTAL_SEND = Metrics.counterMetric(MessageBasedImportService.class, "totalSend");

    @Autowired
    private ImportSettings importTestGroupResultSettings;

    @Autowired
    private MetricsManager metrics;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    @Measured
    @Logged(logLevel = LogLevel.TRACE)
    public void importResult(TestGroupResultData result) {
        jmsTemplate.convertAndSend(importTestGroupResultSettings.getQueueName(), result);
        metrics.increment(TOTAL_SEND);
    }

}
