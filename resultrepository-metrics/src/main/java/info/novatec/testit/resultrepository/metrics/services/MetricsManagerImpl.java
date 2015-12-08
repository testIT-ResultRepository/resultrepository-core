package info.novatec.testit.resultrepository.metrics.services;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

import info.novatec.testit.resultrepository.metrics.api.metrics.CounterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.HistogramMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.MeterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.TimerMetric;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsManager;


@Service
public class MetricsManagerImpl implements MetricsManager {

    @Autowired
    private MetricRegistry metricRegistry;

    @Override
    public void increment(CounterMetric metric) {
        counter(metric).inc();
    }

    @Override
    public void increment(CounterMetric metric, long delta) {
        counter(metric).inc(delta);
    }

    @Override
    public void decrement(CounterMetric metric) {
        counter(metric).dec();
    }

    @Override
    public void decrement(CounterMetric metric, long delta) {
        counter(metric).dec(delta);
    }

    public Counter counter(CounterMetric metric) {
        return metricRegistry.counter(metric.getMetricName());
    }

    @Override
    public void update(HistogramMetric metric, int value) {
        histogram(metric).update(value);
    }

    @Override
    public void update(HistogramMetric metric, long value) {
        histogram(metric).update(value);
    }

    public Histogram histogram(HistogramMetric metric) {
        return metricRegistry.histogram(metric.getMetricName());
    }

    @Override
    public void mark(MeterMetric metric) {
        meter(metric).mark();
    }

    @Override
    public void mark(MeterMetric metric, long count) {
        meter(metric).mark(count);
    }

    public Meter meter(MeterMetric metric) {
        return metricRegistry.meter(metric.getMetricName());
    }

    @Override
    public void time(TimerMetric metric, Runnable runnable) {
        Context timer = timer(metric).time();
        try {
            runnable.run();
        } finally {
            timer.stop();
        }
    }

    @Override
    public <T> T time(TimerMetric metric, Supplier<T> supplier) {
        Context timer = timer(metric).time();
        try {
            return supplier.get();
        } finally {
            timer.stop();
        }
    }

    @Override
    public void update(TimerMetric metric, long duration, TimeUnit unit) {
        timer(metric).update(duration, unit);
    }

    public Timer timer(TimerMetric metric) {
        return metricRegistry.timer(metric.getMetricName());
    }

}
