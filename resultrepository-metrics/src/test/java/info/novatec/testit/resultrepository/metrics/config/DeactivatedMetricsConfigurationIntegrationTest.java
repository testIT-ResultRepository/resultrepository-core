package info.novatec.testit.resultrepository.metrics.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DeactivatedMetricsConfiguration.class)
public class DeactivatedMetricsConfigurationIntegrationTest {

    @Test
    public void testThatContextIsLoaded() {
        // dummy test in order to trigger context load
    }

}
