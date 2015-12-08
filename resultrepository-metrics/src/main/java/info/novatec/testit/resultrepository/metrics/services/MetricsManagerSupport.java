package info.novatec.testit.resultrepository.metrics.services;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.metrics.api.metrics.CounterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.HistogramMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.MeterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.TimerMetric;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;


/**
 * This {@linkplain MetricsManager} implementation is intended to be used in
 * cases where metrics should not be recorded.
 */
@Service
@ConditionalOnMissingBean(MetricsManager.class)
public class MetricsManagerSupport implements MetricsManager {

    @Override
    public void increment(CounterMetric metric) {
        // do nothing
    }

    @Override
    public void increment(CounterMetric metric, long delta) {
        // do nothing
    }

    @Override
    public void decrement(CounterMetric metric) {
        // do nothing
    }

    @Override
    public void decrement(CounterMetric metric, long delta) {
        // do nothing
    }

    @Override
    public void update(HistogramMetric metric, int value) {
        // do nothing
    }

    @Override
    public void update(HistogramMetric metric, long value) {
        // do nothing
    }

    @Override
    public void mark(MeterMetric metric) {
        // do nothing
    }

    @Override
    public void mark(MeterMetric metric, long count) {
        // do nothing
    }

    @Override
    public void time(TimerMetric metric, Runnable runnable) {
        // do nothing
    }

    @Override
    public <T> T time(TimerMetric metric, Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public void update(TimerMetric metric, long duration, TimeUnit unit) {
        // do nothing
    }

}
