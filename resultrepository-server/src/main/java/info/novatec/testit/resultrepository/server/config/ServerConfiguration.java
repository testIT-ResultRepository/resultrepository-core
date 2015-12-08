package info.novatec.testit.resultrepository.server.config;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.server.services.ImportMessageListener;
import info.novatec.testit.resultrepository.server.settings.ImportSettings;
import info.novatec.testit.resultrepository.server.settings.MessagingSettings;


@Configuration
public class ServerConfiguration {

    @Configuration
    @EnableConfigurationProperties
    @ComponentScan("info.novatec.testit.resultrepository.server.settings")
    public static class SettingsConfiguration {
        // settings are automatically exposed as beans via component scan
    }

    @Configuration
    @ComponentScan("info.novatec.testit.resultrepository.server.services")
    public static class ServiceConfiguration {
        // services are automatically exposed as beans via component scan
    }

    @Configuration
    public static class MessagingConfiguration {

        @Autowired
        private MessagingSettings messagingSettings;
        @Autowired
        private ImportSettings importTestGroupResultSettings;

        @Autowired
        private ConnectionFactory connectionFactory;
        @Autowired
        private ImportMessageListener importMessageListener;

        /**
         * @return {@linkplain RetryTemplate} to use when retrying message
         * related operations.
         */
        @Bean
        public RetryTemplate messageRetryTemplate() {

            SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
            retryPolicy.setMaxAttempts(messagingSettings.getMaxRetries());

            ExponentialRandomBackOffPolicy backOffPolicy = new ExponentialRandomBackOffPolicy();
            backOffPolicy.setInitialInterval(messagingSettings.getInitialRetryInterval());
            backOffPolicy.setMultiplier(messagingSettings.getRetryBackOffMultiplier());
            backOffPolicy.setMaxInterval(messagingSettings.getMaxRetryInterval());

            RetryTemplate bean = new RetryTemplate();
            bean.setRetryPolicy(retryPolicy);
            bean.setBackOffPolicy(backOffPolicy);
            return bean;

        }

        /**
         * @return the {@linkplain DefaultMessageListenerContainer message
         * listener container} to use for the import of
         * {@linkplain TestGroupResultData test group results}.
         */
        @Bean
        public DefaultMessageListenerContainer importTestGroupResultMessageListenerContainer() {

            DefaultMessageListenerContainer bean = new DefaultMessageListenerContainer();
            bean.setConnectionFactory(this.connectionFactory);
            bean.setDestinationName(importTestGroupResultSettings.getQueueName());
            bean.setConcurrency(importTestGroupResultSettings.getConcurrency());
            bean.setPubSubDomain(false);
            bean.setMessageListener(importMessageListener);

            return bean;

        }

    }

}
