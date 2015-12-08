package info.novatec.testit.resultrepository.metrics.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class GenericMetricTest {

    private static final String PREFIX = "prefix";
    private static final String NAME_PART = "foo";
    private static final String ADDITIONAL_NAME_PART = "bar";

    @Test
    public void testEqualityOfEqualyNamedMetrics() {

        Metric metric = new GenericMetric(PREFIX, NAME_PART, ADDITIONAL_NAME_PART);

        CounterMetric counter = new GenericMetric(PREFIX, NAME_PART, ADDITIONAL_NAME_PART);
        HistogramMetric histogram = new GenericMetric(PREFIX, NAME_PART, ADDITIONAL_NAME_PART);
        MeterMetric meter = new GenericMetric(PREFIX, NAME_PART, ADDITIONAL_NAME_PART);
        TimerMetric timer = new GenericMetric(PREFIX, NAME_PART, ADDITIONAL_NAME_PART);

        assertThat(metric).isEqualTo(counter).isEqualTo(histogram).isEqualTo(meter).isEqualTo(timer);

    }

}
