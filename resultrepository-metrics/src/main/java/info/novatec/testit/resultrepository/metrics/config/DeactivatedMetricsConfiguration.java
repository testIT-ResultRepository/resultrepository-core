package info.novatec.testit.resultrepository.metrics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;
import info.novatec.testit.resultrepository.metrics.services.MetricsManagerSupport;


@Configuration
public class DeactivatedMetricsConfiguration {

    /* metrics beans */

    @Bean
    public MetricsManager metricsManager() {
        return new MetricsManagerSupport();
    }

}
