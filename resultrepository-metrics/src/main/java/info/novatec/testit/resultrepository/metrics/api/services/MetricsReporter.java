package info.novatec.testit.resultrepository.metrics.api.services;

import java.util.List;

import info.novatec.testit.resultrepository.metrics.api.report.CounterReport;
import info.novatec.testit.resultrepository.metrics.api.report.HistogramReport;
import info.novatec.testit.resultrepository.metrics.api.report.MeterReport;
import info.novatec.testit.resultrepository.metrics.api.report.MetricsReport;
import info.novatec.testit.resultrepository.metrics.api.report.TimerReport;


public interface MetricsReporter {

    MetricsReport report();

    List<CounterReport> reportCounters();

    List<HistogramReport> reportHistograms();

    List<MeterReport> reportMeters();

    List<TimerReport> reportTimers();

}
