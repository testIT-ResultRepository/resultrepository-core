package info.novatec.testit.resultrepository.metrics.api.metrics;

public interface Metric {

    /**
     * The name of the metric - used to uniquely identify the metric. It is
     * recommended to use the fully qualified class name as a prefix for class
     * specific metrics.
     *
     * @return the name of the metric
     */
    String getMetricName();

}
