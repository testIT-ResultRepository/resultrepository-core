package info.novatec.testit.resultrepository.logging.aspects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.logging.aspects.LoggedAspectIntegrationTest.LoggingConfigurationIntegrationTestConfiguration;
import info.novatec.testit.resultrepository.logging.config.LoggingConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { LoggingConfiguration.class,
    LoggingConfigurationIntegrationTestConfiguration.class })
public class LoggedAspectIntegrationTest {

    @Configuration
    public static class LoggingConfigurationIntegrationTestConfiguration {

        @Bean
        public TestService service() {
            return new TestService();
        }

    }

    @SuppressWarnings("unused")
    public static class TestService {

        @Logged(logLevel = LogLevel.INFO, printArguments = false)
        public void loggedMethod(String a, int b) {
            // do nothing
        }

        @Logged(logLevel = LogLevel.INFO, printArguments = true)
        public void loggedMethodWithArguments(String a, int b) {
            // do nothing
        }

    }

    @Autowired
    private TestService service;

    @Test
    public void testThatLoggedAnnotationTiggersTheAspect() {
        service.loggedMethod("foo", 42);
        service.loggedMethodWithArguments("foo", 42);
        // TODO: assertions - how?
    }

}
