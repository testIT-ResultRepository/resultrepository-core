package info.novatec.testit.resultrepository.metrics.api.services;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import info.novatec.testit.resultrepository.metrics.api.metrics.CounterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.HistogramMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.MeterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.TimerMetric;


public interface MetricsManager {

    void increment(CounterMetric metric);

    void increment(CounterMetric metric, long delta);

    void decrement(CounterMetric metric);

    void decrement(CounterMetric metric, long delta);

    void update(HistogramMetric metric, int value);

    void update(HistogramMetric metric, long value);

    void mark(MeterMetric metric);

    void mark(MeterMetric metric, long count);

    void time(TimerMetric metric, Runnable runnable);

    <T> T time(TimerMetric metric, Supplier<T> supplier);

    void update(TimerMetric metric, long duration, TimeUnit unit);

}
