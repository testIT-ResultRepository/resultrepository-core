package info.novatec.testit.resultrepository.api.statistics;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.interfaces.Test;


/**
 * Base implementation of a result statistic object containing the basic
 * properties like number of tests with their respective status or different
 * runtime values like mean or percentiles.
 * <p>
 * This class is intended to be sub classed and cannot be initialized directly.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
public abstract class AbstractResultStatistic implements Serializable {

    /**
     * Timestamp of the creation of this statistic instance.
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * The total number of {@link Test tests} considered for this statistic.
     */
    private int totalNumberOfTests;

    /**
     * The total number of passed {@link Test tests}.
     */
    private int numberOfPassedTests;

    /**
     * The total number of failed {@link Test tests}.
     */
    private int numberOfFailedTests;

    /**
     * The total number of exceptional {@link Test tests}.
     */
    private int numberOfExceptionTests;

    /**
     * The total number of skipped {@link Test tests}.
     */
    private int numberOfSkippedTests;

    /**
     * The total number of {@link Test tests} with an unknown result.
     */
    private int numberOfUnknownTests;

    /**
     * The total runtime of all {@link Test tests} considered for this
     * statistic.
     */
    private long totalTestRuntime;

    /**
     * The runtime of the longest running {@link Test test} considered for this
     * statistic.
     */
    private long maxTestRuntime;

    /**
     * The runtime of the shortest running {@link Test test} considered for this
     * statistic.
     */
    private long minTestRuntime;

    /**
     * The mean runtime of all {@link Test tests} considered for this statistic.
     */
    private double meanTestRuntime;

    /**
     * The median (50th percentile) runtime of all {@link Test tests} considered
     * for this statistic.
     */
    private double medianTestRuntime;

    /**
     * The 75th percentile runtime of all {@link Test tests} considered for this
     * statistic.
     */
    private double the75thPercentile;

    /**
     * The 95th percentile runtime of all {@link Test tests} considered for this
     * statistic.
     */
    private double the95thPercentile;

    /**
     * The 98th percentile runtime of all {@link Test tests} considered for this
     * statistic.
     */
    private double the98thPercentile;

    /**
     * The 99th percentile runtime of all {@link Test tests} considered for this
     * statistic.
     */
    private double the99thPercentile;

    /**
     * The 99.9th percentile runtime of all {@link Test tests} considered for
     * this statistic.
     */
    private double the999thPercentile;

    protected AbstractResultStatistic() {
        // should not be initialized directly
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotalNumberOfTests() {
        return totalNumberOfTests;
    }

    public void setTotalNumberOfTests(int totalNumberOfTests) {
        this.totalNumberOfTests = totalNumberOfTests;
    }

    public int getNumberOfPassedTests() {
        return numberOfPassedTests;
    }

    public void setNumberOfPassedTests(int numberOfPassedTests) {
        this.numberOfPassedTests = numberOfPassedTests;
    }

    public int getNumberOfFailedTests() {
        return numberOfFailedTests;
    }

    public void setNumberOfFailedTests(int numberOfFailedTests) {
        this.numberOfFailedTests = numberOfFailedTests;
    }

    public int getNumberOfExceptionTests() {
        return numberOfExceptionTests;
    }

    public void setNumberOfExceptionTests(int numberOfExceptionTests) {
        this.numberOfExceptionTests = numberOfExceptionTests;
    }

    public int getNumberOfSkippedTests() {
        return numberOfSkippedTests;
    }

    public void setNumberOfSkippedTests(int numberOfSkippedTests) {
        this.numberOfSkippedTests = numberOfSkippedTests;
    }

    public int getNumberOfUnknownTests() {
        return numberOfUnknownTests;
    }

    public void setNumberOfUnknownTests(int numberOfUnknownTests) {
        this.numberOfUnknownTests = numberOfUnknownTests;
    }

    public long getTotalTestRuntime() {
        return totalTestRuntime;
    }

    public void setTotalTestRuntime(long totalTestRuntime) {
        this.totalTestRuntime = totalTestRuntime;
    }

    public long getMaxTestRuntime() {
        return maxTestRuntime;
    }

    public void setMaxTestRuntime(long maxTestRuntime) {
        this.maxTestRuntime = maxTestRuntime;
    }

    public long getMinTestRuntime() {
        return minTestRuntime;
    }

    public void setMinTestRuntime(long minTestRuntime) {
        this.minTestRuntime = minTestRuntime;
    }

    public double getMeanTestRuntime() {
        return meanTestRuntime;
    }

    public void setMeanTestRuntime(double meanTestRuntime) {
        this.meanTestRuntime = meanTestRuntime;
    }

    public double getMedianTestRuntime() {
        return medianTestRuntime;
    }

    public void setMedianTestRuntime(double medianTestRuntime) {
        this.medianTestRuntime = medianTestRuntime;
    }

    public double getThe75thPercentile() {
        return the75thPercentile;
    }

    public void setThe75thPercentile(double the75thPercentile) {
        this.the75thPercentile = the75thPercentile;
    }

    public double getThe95thPercentile() {
        return the95thPercentile;
    }

    public void setThe95thPercentile(double the95thPercentile) {
        this.the95thPercentile = the95thPercentile;
    }

    public double getThe98thPercentile() {
        return the98thPercentile;
    }

    public void setThe98thPercentile(double the98thPercentile) {
        this.the98thPercentile = the98thPercentile;
    }

    public double getThe99thPercentile() {
        return the99thPercentile;
    }

    public void setThe99thPercentile(double the99thPercentile) {
        this.the99thPercentile = the99thPercentile;
    }

    public double getThe999thPercentile() {
        return the999thPercentile;
    }

    public void setThe999thPercentile(double the999thPercentile) {
        this.the999thPercentile = the999thPercentile;
    }

}
