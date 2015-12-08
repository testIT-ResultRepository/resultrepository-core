package info.novatec.testit.resultrepository.persistence.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.metrics.config.DeactivatedMetricsConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DeactivatedMetricsConfiguration.class, PersistenceConfiguration.class })
@TestPropertySource(properties = { "resultrepository.persistence.data-folder=target/data/contextTest" })
public class PersistenceConfigurationIntegrationTest {

    @Test
    public void testThatContextIsLoaded() {
        // dummy test in order to trigger context load
    }

}
