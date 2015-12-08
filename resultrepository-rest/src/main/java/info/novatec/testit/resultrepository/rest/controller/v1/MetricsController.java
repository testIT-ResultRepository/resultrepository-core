package info.novatec.testit.resultrepository.rest.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.testit.resultrepository.metrics.api.report.CounterReport;
import info.novatec.testit.resultrepository.metrics.api.report.HistogramReport;
import info.novatec.testit.resultrepository.metrics.api.report.MeterReport;
import info.novatec.testit.resultrepository.metrics.api.report.MetricsReport;
import info.novatec.testit.resultrepository.metrics.api.report.TimerReport;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsReporter;
import info.novatec.testit.resultrepository.remote.v1.MetricsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MetricsController implements MetricsRemoteService {

    @Autowired
    private MetricsReporter reporter;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.METRICS, method = RequestMethod.GET)
    public
    @ResponseBody
    MetricsReport report() {
        return reporter.report();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.COUNTER_METRICS, method = RequestMethod.GET)
    public
    @ResponseBody
    List<CounterReport> reportCounters() {
        return reporter.reportCounters();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.HISTOGRAM_METRICS, method = RequestMethod.GET)
    public
    @ResponseBody
    List<HistogramReport> reportHistograms() {
        return reporter.reportHistograms();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.METER_METRICS, method = RequestMethod.GET)
    public
    @ResponseBody
    List<MeterReport> reportMeters() {
        return reporter.reportMeters();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TIMER_METRICS, method = RequestMethod.GET)
    public
    @ResponseBody
    List<TimerReport> reportTimers() {
        return reporter.reportTimers();
    }

}
