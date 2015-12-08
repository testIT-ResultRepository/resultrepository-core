package info.novatec.testit.resultrepository.metrics.api.report;

import javax.xml.bind.annotation.XmlRootElement;

import com.codahale.metrics.Meter;


@XmlRootElement
@SuppressWarnings({ "serial", "CPD-START" })
public class MeterReport extends AbstractReport {

    private double meanRate;
    private double oneMinuteRate;
    private double fiveMinuteRate;
    private double fifteenMinuteRate;

    public MeterReport() {
        // for serialization
    }

    public MeterReport(String name, Meter meter) {
        super(name, meter.getCount());
        this.meanRate = meter.getMeanRate();
        this.oneMinuteRate = meter.getOneMinuteRate();
        this.fiveMinuteRate = meter.getFiveMinuteRate();
        this.fifteenMinuteRate = meter.getFifteenMinuteRate();
    }

    public double getMeanRate() {
        return meanRate;
    }

    public void setMeanRate(double meanRate) {
        this.meanRate = meanRate;
    }

    public double getOneMinuteRate() {
        return oneMinuteRate;
    }

    public void setOneMinuteRate(double oneMinuteRate) {
        this.oneMinuteRate = oneMinuteRate;
    }

    public double getFiveMinuteRate() {
        return fiveMinuteRate;
    }

    public void setFiveMinuteRate(double fiveMinuteRate) {
        this.fiveMinuteRate = fiveMinuteRate;
    }

    public double getFifteenMinuteRate() {
        return fifteenMinuteRate;
    }

    public void setFifteenMinuteRate(double fifteenMinuteRate) {
        this.fifteenMinuteRate = fifteenMinuteRate;
    }

}
