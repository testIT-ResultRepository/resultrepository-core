package info.novatec.testit.resultrepository.persistence.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
public class PersistenceConfiguration {

    @Configuration
    @ConditionalOnClass(HealthIndicator.class)
    @ComponentScan("info.novatec.testit.resultrepository.persistence.actuator")
    public static class ActuatorConfiguration {
        // actuator beans are automatically loaded via component scan
    }

    @Configuration
    @ConditionalOnClass(value = Aspect.class)
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    @ComponentScan("info.novatec.testit.resultrepository.persistence.aspects")
    public static class AspectConfiguration {
        // aspects are automatically exposed as beans via component scan
    }

    @Configuration
    @EnableConfigurationProperties
    @ComponentScan("info.novatec.testit.resultrepository.persistence.settings")
    public static class SettingsConfiguration {
        // settings are automatically exposed as beans via component scan
    }

    @Configuration
    @ComponentScan("info.novatec.testit.resultrepository.persistence.services")
    public static class ServiceConfiguration {
        // services are automatically exposed as beans via component scan
    }

}
