package info.novatec.testit.resultrepository.server.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;


/**
 * All {@linkplain ConfigurationProperties configuration properties} which
 * relate to the import of {@link TestGroupResultData test group results}.
 */
@Component
@ConfigurationProperties(prefix = "resultrepository.import")
public class ImportSettings {

    private String queueName = "importTestGroupResult";
    private String concurrency = "1-1";

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setConcurrency(String concurrency) {
        this.concurrency = concurrency;
    }

    public String getConcurrency() {
        return concurrency;
    }

}
