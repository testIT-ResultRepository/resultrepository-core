package info.novatec.testit.resultrepository.rest.controller.v1;

import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.COUNTER_METRICS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.HISTOGRAM_METRICS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.METER_METRICS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.METRICS;
import static info.novatec.testit.resultrepository.remote.v1.V1ContextPaths.TIMER_METRICS;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.resultrepository.metrics.api.report.CounterReport;
import info.novatec.testit.resultrepository.metrics.api.report.HistogramReport;
import info.novatec.testit.resultrepository.metrics.api.report.MeterReport;
import info.novatec.testit.resultrepository.metrics.api.report.MetricsReport;
import info.novatec.testit.resultrepository.metrics.api.report.TimerReport;
import info.novatec.testit.resultrepository.metrics.api.services.MetricsReporter;


@RunWith(MockitoJUnitRunner.class)
public class MetricsControllerTest extends AbstractControllerTest {

    @Mock
    MetricsReporter reporter;

    @InjectMocks
    MetricsController cut;

    @Override
    Object getClassUnderTest() {
        return cut;
    }

    @Test
    public void testController_report() throws Exception {

        MetricsReport report = new MetricsReport();
        doReturn(report).when(reporter).report();

        performAndAssertGet(METRICS, report);

    }

    @Test
    public void testController_reportCounters() throws Exception {

        CounterReport report1 = new CounterReport();
        CounterReport report2 = new CounterReport();
        List<CounterReport> reports = Arrays.asList(report1, report2);
        doReturn(reports).when(reporter).reportCounters();

        performAndAssertGet(COUNTER_METRICS, reports);

    }

    @Test
    public void testController_reportHistograms() throws Exception {

        HistogramReport report1 = new HistogramReport();
        HistogramReport report2 = new HistogramReport();
        List<HistogramReport> reports = Arrays.asList(report1, report2);
        doReturn(reports).when(reporter).reportHistograms();

        performAndAssertGet(HISTOGRAM_METRICS, reports);

    }

    @Test
    public void testController_reportMeters() throws Exception {

        MeterReport report1 = new MeterReport();
        MeterReport report2 = new MeterReport();
        List<MeterReport> reports = Arrays.asList(report1, report2);
        doReturn(reports).when(reporter).reportMeters();

        performAndAssertGet(METER_METRICS, reports);

    }

    @Test
    public void testController_reportTimers() throws Exception {

        TimerReport report1 = new TimerReport();
        TimerReport report2 = new TimerReport();
        List<TimerReport> reports = Arrays.asList(report1, report2);
        doReturn(reports).when(reporter).reportTimers();

        performAndAssertGet(TIMER_METRICS, reports);

    }

}
