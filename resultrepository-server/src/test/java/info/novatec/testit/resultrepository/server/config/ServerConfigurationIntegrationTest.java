package info.novatec.testit.resultrepository.server.config;

import static org.mockito.Mockito.mock;

import javax.jms.ConnectionFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.metrics.config.DeactivatedMetricsConfiguration;
import info.novatec.testit.resultrepository.server.api.ImportService;
import info.novatec.testit.resultrepository.server.config.ServerConfigurationIntegrationTest.ServerConfigurationIntegrationTestConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DeactivatedMetricsConfiguration.class, ServerConfiguration.class,
    ServerConfigurationIntegrationTestConfiguration.class })
public class ServerConfigurationIntegrationTest {

    @Configuration
    public static class ServerConfigurationIntegrationTestConfiguration {

        @Bean
        public ConnectionFactory connectionFactory() {
            return mock(ConnectionFactory.class);
        }

        @Bean
        public ImportService importService() {
            return mock(ImportService.class);
        }

        @Bean
        public JmsTemplate jmsTemplate() {
            return mock(JmsTemplate.class);
        }

    }

    @Test
    public void testThatContextIsLoaded() {
        // dummy test in order to trigger context load
    }

}
