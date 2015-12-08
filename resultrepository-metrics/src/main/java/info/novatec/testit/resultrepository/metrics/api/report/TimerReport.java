package info.novatec.testit.resultrepository.metrics.api.report;

import java.util.concurrent.TimeUnit;
import javax.xml.bind.annotation.XmlRootElement;

import com.codahale.metrics.Timer;


@XmlRootElement
@SuppressWarnings({ "serial", "CPD-START" })
public class TimerReport extends AbstractSnapshotReport {

    private double meanRate;
    private double oneMinuteRate;
    private double fiveMinuteRate;
    private double fifteenMinuteRate;

    public TimerReport() {
        // for serialization
    }

    public TimerReport(String name, Timer timer) {
        super(name, timer.getCount(), timer.getSnapshot(), TimeUnit.MILLISECONDS.toNanos(1));
        this.meanRate = timer.getMeanRate();
        this.oneMinuteRate = timer.getOneMinuteRate();
        this.fiveMinuteRate = timer.getFiveMinuteRate();
        this.fifteenMinuteRate = timer.getFifteenMinuteRate();
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
