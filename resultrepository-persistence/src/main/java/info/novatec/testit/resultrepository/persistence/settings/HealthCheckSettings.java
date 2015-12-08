package info.novatec.testit.resultrepository.persistence.settings;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * All {@linkplain ConfigurationProperties configuration properties} which
 * relate to health checks.
 */
@Component
@ConditionalOnClass(HealthIndicator.class)
@ConfigurationProperties(prefix = "resultrepository.health")
public class HealthCheckSettings {

    private long timeout = 5000;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
