package info.novatec.testit.resultrepository.metrics.api.report;

import com.codahale.metrics.Snapshot;


@SuppressWarnings({ "serial", "CPD-START" })
public abstract class AbstractSnapshotReport extends AbstractReport {

    private double the75thPercentile;
    private double the95thPercentile;
    private double the98thPercentile;
    private double the99thPercentile;
    private double the999thPercentile;
    private long max;
    private double mean;
    private double median;
    private long min;
    private double stdDev;

    protected AbstractSnapshotReport() {
        // for serialization
    }

    protected AbstractSnapshotReport(String name, long count, Snapshot snapshot, long devisor) {
        super(name, count);
        this.the75thPercentile = snapshot.get75thPercentile() / devisor;
        this.the95thPercentile = snapshot.get95thPercentile() / devisor;
        this.the98thPercentile = snapshot.get98thPercentile() / devisor;
        this.the99thPercentile = snapshot.get99thPercentile() / devisor;
        this.the999thPercentile = snapshot.get999thPercentile() / devisor;
        this.max = snapshot.getMax() / devisor;
        this.mean = snapshot.getMean() / devisor;
        this.median = snapshot.getMedian() / devisor;
        this.min = snapshot.getMin() / devisor;
        this.stdDev = snapshot.getStdDev() / devisor;
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

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

}
