package info.novatec.testit.resultrepository.metrics.aspects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.metrics.api.metrics.Metrics;
import info.novatec.testit.resultrepository.metrics.aspects.MeasuredAspectIntegrationTest.MeasuredAspectIntegrationTestConfiguration;
import info.novatec.testit.resultrepository.metrics.config.MetricsConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MetricsConfiguration.class, MeasuredAspectIntegrationTestConfiguration.class })
public class MeasuredAspectIntegrationTest {

    @Configuration
    public static class MeasuredAspectIntegrationTestConfiguration {

        @Bean
        public TestService service() {
            return new TestService();
        }

    }

    public static class TestService {

        @Measured
        public void measuredMethod() throws InterruptedException {
            Thread.sleep(5);
        }

    }

    @Autowired
    private MetricRegistry metricRegistry;

    @Autowired
    private TestService service;

    @Test
    public void testThatMeasureExecutionAnnotationTriggersTheAspect() throws InterruptedException {

        service.measuredMethod();
        service.measuredMethod();
        service.measuredMethod();

        String metricName = Metrics.timerMetric(TestService.class, "measuredMethod").getMetricName();
        Timer timer = metricRegistry.getTimers().get(metricName);

        assertThat(timer).isNotNull();
        assertThat(timer.getCount()).isEqualTo(3);

    }

}
