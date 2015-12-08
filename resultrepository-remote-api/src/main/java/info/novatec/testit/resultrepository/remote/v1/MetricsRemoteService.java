package info.novatec.testit.resultrepository.remote.v1;

import java.util.List;

import info.novatec.testit.resultrepository.metrics.api.metrics.CounterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.HistogramMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.MeterMetric;
import info.novatec.testit.resultrepository.metrics.api.metrics.TimerMetric;
import info.novatec.testit.resultrepository.metrics.api.report.CounterReport;
import info.novatec.testit.resultrepository.metrics.api.report.HistogramReport;
import info.novatec.testit.resultrepository.metrics.api.report.MeterReport;
import info.novatec.testit.resultrepository.metrics.api.report.MetricsReport;
import info.novatec.testit.resultrepository.metrics.api.report.TimerReport;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding the
 * retrieval of metrics from the ResultRepository.
 *
 * @since 2.0.0
 */
public interface MetricsRemoteService {

    /**
     * Returns all available metrics in the form of a {@link MetricsReport
     * report}.
     *
     * @return the report
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    MetricsReport report() throws RemoteOperationException;

    /**
     * Returns all {@link CounterMetric counter metrics} as a list of
     * {@link CounterReport reports}.
     *
     * @return the reports
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    List<CounterReport> reportCounters() throws RemoteOperationException;

    /**
     * Returns all {@link HistogramMetric histogram metrics} as a list of
     * {@link HistogramReport reports}.
     *
     * @return the reports
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    List<HistogramReport> reportHistograms() throws RemoteOperationException;

    /**
     * Returns all {@link MeterMetric meter metrics} as a list of
     * {@link MeterReport reports}.
     *
     * @return the reports
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    List<MeterReport> reportMeters() throws RemoteOperationException;

    /**
     * Returns all {@link TimerMetric timer metrics} as a list of
     * {@link TimerReport reports}.
     *
     * @return the reports
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    List<TimerReport> reportTimers() throws RemoteOperationException;

}
