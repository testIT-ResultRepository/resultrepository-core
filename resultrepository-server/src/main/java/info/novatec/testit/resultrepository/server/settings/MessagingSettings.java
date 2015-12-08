package info.novatec.testit.resultrepository.server.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * All {@linkplain ConfigurationProperties configuration properties} which
 * relate to general messaging activities.
 */
@Component
@ConfigurationProperties(prefix = "resultrepository.messaging")
public class MessagingSettings {

    private int maxRetries = 10;
    private long initialRetryInterval = 100;
    private double retryBackOffMultiplier = 1.4;
    private long maxRetryInterval = 2000;

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public long getInitialRetryInterval() {
        return initialRetryInterval;
    }

    public void setInitialRetryInterval(long initialRetryInterval) {
        this.initialRetryInterval = initialRetryInterval;
    }

    public double getRetryBackOffMultiplier() {
        return retryBackOffMultiplier;
    }

    public void setRetryBackOffMultiplier(double retryBackOffMultiplier) {
        this.retryBackOffMultiplier = retryBackOffMultiplier;
    }

    public long getMaxRetryInterval() {
        return maxRetryInterval;
    }

    public void setMaxRetryInterval(long maxRetryInterval) {
        this.maxRetryInterval = maxRetryInterval;
    }

}
