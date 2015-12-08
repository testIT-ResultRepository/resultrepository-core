package info.novatec.testit.resultrepository.metrics.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.codahale.metrics.MetricRegistry;


@Configuration
public class MetricsConfiguration {

    @Configuration
    @ConditionalOnClass(value = Aspect.class)
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    @ComponentScan("info.novatec.testit.resultrepository.metrics.aspects")
    public static class AspectConfiguration {
        // aspects are automatically exposed as beans via component scan
    }

    @Configuration
    @ComponentScan("info.novatec.testit.resultrepository.metrics.services")
    public static class ServiceConfiguration {

        // services are automatically exposed as beans via component scan

        @Bean
        public MetricRegistry metricRegistry() {
            // external class
            return new MetricRegistry();
        }

    }

}
