package info.novatec.testit.resultrepository.metrics.api.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import javax.xml.bind.JAXBException;

import org.junit.Test;


public abstract class AbstractSerializationTest {

    @Test
    public void testSerializationOfCounterReport() throws Exception {

        CounterReport report = counterReport();

        CounterReport biDirectionalSerializedReport = biDirectionalSerialization(report);
        assertThat(biDirectionalSerializedReport).isEqualToComparingFieldByField(report);

    }

    @Test
    public void testSerializationOfHistogramReport() throws Exception {

        HistogramReport report = histogramReport();

        HistogramReport biDirectionalSerializedReport = biDirectionalSerialization(report);
        assertThat(biDirectionalSerializedReport).isEqualToComparingFieldByField(report);

    }

    @Test
    public void testSerializationOfMeterReport() throws Exception {

        MeterReport report = meterReport();

        MeterReport biDirectionalSerializedReport = biDirectionalSerialization(report);
        assertThat(biDirectionalSerializedReport).isEqualToComparingFieldByField(report);

    }

    @Test
    public void testSerializationOfTimerReport() throws Exception {

        TimerReport report = timerReport();

        TimerReport biDirectionalSerializedReport = biDirectionalSerialization(report);
        assertThat(biDirectionalSerializedReport).isEqualToComparingFieldByField(report);

    }

    @Test
    public void testSerializationOfMetricsReport() throws Exception {

        MetricsReport report = new MetricsReport();
        report.addCounterReport(counterReport());
        report.addCounterReport(counterReport());
        report.addHistogramReport(histogramReport());
        report.addHistogramReport(histogramReport());
        report.addMeterReport(meterReport());
        report.addMeterReport(meterReport());
        report.addTimerReports(timerReport());
        report.addTimerReports(timerReport());

        biDirectionalSerialization(report);

        /* No assert because reports don't implement equals. It should be enough
         * if the above operation doesn't throw an exception since the only
         * content of the MetricReport is lists of other reports which can be
         * serialized.. */

    }

    protected abstract <T> T biDirectionalSerialization(T object) throws IOException, JAXBException;

    /* utilities */

    private CounterReport counterReport() {
        CounterReport report = new CounterReport();
        report.setName("counterMetric");
        report.setTimestamp(500000L);
        report.setCount(1000L);
        return report;
    }

    private HistogramReport histogramReport() {
        HistogramReport report = new HistogramReport();
        report.setName("histogramMetric");
        report.setTimestamp(500000L);
        report.setCount(1000L);
        report.setMax(10);
        report.setMean(5);
        report.setMedian(5);
        report.setMin(1);
        report.setStdDev(4);
        report.setThe75thPercentile(6);
        report.setThe95thPercentile(7);
        report.setThe98thPercentile(8);
        report.setThe99thPercentile(9);
        report.setThe999thPercentile(10);
        return report;
    }

    private MeterReport meterReport() {
        MeterReport report = new MeterReport();
        report.setName("meterReport");
        report.setTimestamp(500000L);
        report.setCount(1000L);
        report.setMeanRate(5);
        report.setOneMinuteRate(6);
        report.setFiveMinuteRate(7);
        report.setFifteenMinuteRate(8);
        return report;
    }

    private TimerReport timerReport() {
        TimerReport report = new TimerReport();
        report.setName("meterReport");
        report.setTimestamp(500000L);
        report.setCount(1000L);
        report.setMeanRate(5);
        report.setOneMinuteRate(6);
        report.setFiveMinuteRate(7);
        report.setFifteenMinuteRate(8);
        report.setMax(10);
        report.setMean(5);
        report.setMedian(5);
        report.setMin(1);
        report.setStdDev(4);
        report.setThe75thPercentile(6);
        report.setThe95thPercentile(7);
        report.setThe98thPercentile(8);
        report.setThe99thPercentile(9);
        report.setThe999thPercentile(10);
        return report;
    }

}
