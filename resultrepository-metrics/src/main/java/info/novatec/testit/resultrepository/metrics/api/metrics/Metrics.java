package info.novatec.testit.resultrepository.metrics.api.metrics;

public final class Metrics {

    private static final String PREFIX_COUNTER = "counter";
    private static final String PREFIX_HISTOGRAM = "histogram";
    private static final String PREFIX_METER = "meter";
    private static final String PREFIX_TIMER = "timer";

    private Metrics() {
        // empty utility constructor
    }

    public static CounterMetric counterMetric(Class<?> clazz, String... nameParts) {
        return new GenericMetric(PREFIX_COUNTER, clazz, nameParts);
    }

    public static CounterMetric counterMetric(String name, String... additionalNameParts) {
        return new GenericMetric(PREFIX_COUNTER, name, additionalNameParts);
    }

    public static HistogramMetric histogramMetric(Class<?> clazz, String... nameParts) {
        return new GenericMetric(PREFIX_HISTOGRAM, clazz, nameParts);
    }

    public static HistogramMetric histogramMetric(String name, String... additionalNameParts) {
        return new GenericMetric(PREFIX_HISTOGRAM, name, additionalNameParts);
    }

    public static MeterMetric meterMetric(Class<?> clazz, String... nameParts) {
        return new GenericMetric(PREFIX_METER, clazz, nameParts);
    }

    public static MeterMetric meterMetric(String name, String... additionalNameParts) {
        return new GenericMetric(PREFIX_METER, name, additionalNameParts);
    }

    public static TimerMetric timerMetric(Class<?> clazz, String... nameParts) {
        return new GenericMetric(PREFIX_TIMER, clazz, nameParts);
    }

    public static TimerMetric timerMetric(String name, String... additionalNameParts) {
        return new GenericMetric(PREFIX_TIMER, name, additionalNameParts);
    }

}
