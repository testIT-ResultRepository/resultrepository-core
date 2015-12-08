package info.novatec.testit.resultrepository.metrics.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.MetricRegistry;

import info.novatec.testit.resultrepository.metrics.api.report.CounterReport;
import info.novatec.testit.resultrepository.metrics.api.report.HistogramReport;
import info.novatec.testit.resultrepository.metrics.api.report.MeterReport;
import info.novatec.testit.resultrepository.metrics.api.report.MetricsReport;
import info.novatec.testit.resultrepository.metrics.api.report.TimerReport;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsReporter;


@Service
public class MetricsReporterImpl implements MetricsReporter {

    @Autowired
    private MetricRegistry metricRegistry;

    @Override
    public MetricsReport report() {
        MetricsReport report = new MetricsReport();
        report.addCounterReports(reportCounters());
        report.addHistogramReports(reportHistograms());
        report.addMeterReports(reportMeters());
        report.addTimerReports(reportTimers());
        return report;
    }

    @Override
    public List<CounterReport> reportCounters() {
        List<CounterReport> reports = new LinkedList<>();
        metricRegistry.getCounters().forEach((name, counter) -> reports.add(new CounterReport(name, counter)));
        return reports;
    }

    @Override
    public List<HistogramReport> reportHistograms() {
        List<HistogramReport> reports = new LinkedList<>();
        metricRegistry.getHistograms().forEach((name, histogram) -> reports.add(new HistogramReport(name, histogram)));
        return reports;
    }

    @Override
    public List<MeterReport> reportMeters() {
        List<MeterReport> reports = new LinkedList<>();
        metricRegistry.getMeters().forEach((name, meter) -> reports.add(new MeterReport(name, meter)));
        return reports;
    }

    @Override
    public List<TimerReport> reportTimers() {
        List<TimerReport> reports = new LinkedList<>();
        metricRegistry.getTimers().forEach((name, timer) -> reports.add(new TimerReport(name, timer)));
        return reports;
    }

}
