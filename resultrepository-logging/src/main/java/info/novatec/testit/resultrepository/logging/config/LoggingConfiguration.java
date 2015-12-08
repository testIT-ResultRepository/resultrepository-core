package info.novatec.testit.resultrepository.logging.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
public class LoggingConfiguration {

    @Configuration
    @ConditionalOnClass(value = Aspect.class)
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    @ComponentScan("info.novatec.testit.resultrepository.logging.aspects")
    public static class AspectConfiguration {
        // aspects are automatically exposed as beans via component scan
    }

}
