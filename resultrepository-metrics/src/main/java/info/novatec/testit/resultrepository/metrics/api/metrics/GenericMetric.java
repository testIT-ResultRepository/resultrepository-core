package info.novatec.testit.resultrepository.metrics.api.metrics;

/**
 * Class which implements all {@linkplain Metric} type interfaces. This is used to
 * create all types of metrics more easily.
 *
 * @see CounterMetric
 * @see HistogramMetric
 * @see MeterMetric
 * @see TimerMetric
 */
public class GenericMetric implements CounterMetric, HistogramMetric, MeterMetric, TimerMetric {

    static final char SEPARATOR = '.';

    private String metricName;

    public GenericMetric(String prefix, Class<?> clazz, String... nameParts) {
        this(prefix, clazz.getName(), nameParts);
    }

    public GenericMetric(String prefix, String name, String... additionalNameParts) {
        StringBuilder buffer = new StringBuilder(prefix);
        buffer.append(SEPARATOR).append(name);
        for (String part : additionalNameParts) {
            buffer.append(SEPARATOR).append(part);
        }
        this.metricName = buffer.toString();
    }

    @Override
    public String getMetricName() {
        return metricName;
    }

    @Override
    public String toString() {
        return getMetricName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((metricName == null) ? 0 : metricName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Metric)) {
            return false;
        }
        Metric other = ( Metric ) obj;
        if (metricName == null) {
            if (other.getMetricName() != null) {
                return false;
            }
        } else if (!metricName.equals(other.getMetricName())) {
            return false;
        }
        return true;
    }

}
