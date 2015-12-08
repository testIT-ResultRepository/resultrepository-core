package info.novatec.testit.resultrepository.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import info.novatec.testit.resultrepository.logging.config.LoggingConfiguration;
import info.novatec.testit.resultrepository.metrics.config.MetricsConfiguration;
import info.novatec.testit.resultrepository.persistence.config.PersistenceConfiguration;
import info.novatec.testit.resultrepository.rest.config.RestConfiguration;
import info.novatec.testit.resultrepository.server.config.ServerConfiguration;


public final class Application {

    @Configuration
    @EnableAutoConfiguration
    @Import({ LoggingConfiguration.class, MetricsConfiguration.class, PersistenceConfiguration.class,
        RestConfiguration.class, ServerConfiguration.class })
    public interface ApplicationConfiguration {
        // java configuration class for the application
    }

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        start(args);
    }

    public static void start(String... args) {
        context = SpringApplication.run(ApplicationConfiguration.class, args);
    }

    public static void stop() {
        context.close();
    }

    private Application() {
        // utility constructor
    }

}
