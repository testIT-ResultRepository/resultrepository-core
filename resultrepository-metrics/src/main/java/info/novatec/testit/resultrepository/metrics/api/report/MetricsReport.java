package info.novatec.testit.resultrepository.metrics.api.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@SuppressWarnings("serial")
public class MetricsReport implements Serializable {

    private List<CounterReport> counterReports = new ArrayList<>();
    private List<HistogramReport> histogramReports = new ArrayList<>();
    private List<MeterReport> meterReports = new ArrayList<>();
    private List<TimerReport> timerReports = new ArrayList<>();

    public List<CounterReport> getCounterReports() {
        return counterReports;
    }

    public void setCounterReports(List<CounterReport> counterReports) {
        this.counterReports = counterReports;
    }

    public MetricsReport addCounterReport(CounterReport report) {
        counterReports.add(report);
        return this;
    }

    public MetricsReport addCounterReports(CounterReport... reports) {
        return addCounterReports(Arrays.asList(reports));
    }

    public MetricsReport addCounterReports(Collection<CounterReport> reports) {
        counterReports.addAll(reports);
        return this;
    }

    public List<HistogramReport> getHistogramReports() {
        return histogramReports;
    }

    public void setHistogramReports(List<HistogramReport> histogramReports) {
        this.histogramReports = histogramReports;
    }

    public MetricsReport addHistogramReport(HistogramReport report) {
        histogramReports.add(report);
        return this;
    }

    public MetricsReport addHistogramReports(HistogramReport... reports) {
        return addHistogramReports(Arrays.asList(reports));
    }

    public MetricsReport addHistogramReports(Collection<HistogramReport> reports) {
        histogramReports.addAll(reports);
        return this;
    }

    public List<MeterReport> getMeterReports() {
        return meterReports;
    }

    public void setMeterReports(List<MeterReport> meterReports) {
        this.meterReports = meterReports;
    }

    public MetricsReport addMeterReport(MeterReport report) {
        meterReports.add(report);
        return this;
    }

    public MetricsReport addMeterReports(MeterReport... reports) {
        return addMeterReports(Arrays.asList(reports));
    }

    public MetricsReport addMeterReports(Collection<MeterReport> reports) {
        meterReports.addAll(reports);
        return this;
    }

    public List<TimerReport> getTimerReports() {
        return timerReports;
    }

    public void setTimerReports(List<TimerReport> timerReports) {
        this.timerReports = timerReports;
    }

    public MetricsReport addTimerReports(TimerReport report) {
        timerReports.add(report);
        return this;
    }

    public MetricsReport addTimerReports(TimerReport... reports) {
        return addTimerReports(Arrays.asList(reports));
    }

    public MetricsReport addTimerReports(Collection<TimerReport> reports) {
        timerReports.addAll(reports);
        return this;
    }

}
