package info.novatec.testit.resultrepository.persistence.utils.statistics;

import static java.lang.Math.floor;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.api.statistics.AbstractResultStatistic;


public abstract class AbstractResultStatisticBuilder<T extends AbstractResultStatistic> {

    private Map<ResultStatus, Integer> statusCounter = new HashMap<>();
    private List<Long> testRuntimes = new LinkedList<>();
    private Long timestamp = System.currentTimeMillis();

    public void addResultData(TestResult result) {
        addResultToStatusCounter(result);
        addResultToDurationList(result);
    }

    private void addResultToStatusCounter(TestResult result) {
        ResultStatus status = result.getStatus();
        Integer value = statusCounter.get(status);
        if (value == null) {
            value = 0;
        }
        statusCounter.put(status, value + 1);
    }

    private void addResultToDurationList(TestResult result) {
        long duration = result.getDuration() != null ? result.getDuration() : 0;
        testRuntimes.add(duration);
    }

    public T build() {

        T statistic = buildBaseStatistic();

        statistic.setTimestamp(timestamp);

        int totalNumberOfTests = statusCounter.values().stream().mapToInt(result -> result).sum();
        statistic.setTotalNumberOfTests(totalNumberOfTests);
        statistic.setNumberOfExceptionTests(statusCounter.getOrDefault(ResultStatus.EXCEPTION, 0));
        statistic.setNumberOfFailedTests(statusCounter.getOrDefault(ResultStatus.FAILED, 0));
        statistic.setNumberOfPassedTests(statusCounter.getOrDefault(ResultStatus.PASSED, 0));
        statistic.setNumberOfSkippedTests(statusCounter.getOrDefault(ResultStatus.SKIPPED, 0));
        statistic.setNumberOfUnknownTests(statusCounter.getOrDefault(ResultStatus.UNKNOWN, 0));

        long[] runtimes = new long[testRuntimes.size()];
        long sum = 0;
        long min = 0;
        long max = 0;
        double mean = 0.0d;

        Collections.sort(testRuntimes);

        int pos = 0;
        for (Long runtime : testRuntimes) {
            runtimes[pos++] = runtime;
            min = Math.min(min, runtime);
            max = Math.max(min, runtime);
            sum += runtime;
        }

        if (runtimes.length > 0) {
            mean = ( double ) sum / runtimes.length;
        }

        statistic.setTotalTestRuntime(sum);
        statistic.setMinTestRuntime(min);
        statistic.setMaxTestRuntime(max);
        statistic.setMeanTestRuntime(mean);

        statistic.setMedianTestRuntime(getQuantilRuntime(runtimes, 0.5d));
        statistic.setThe75thPercentile(getQuantilRuntime(runtimes, 0.75d));
        statistic.setThe95thPercentile(getQuantilRuntime(runtimes, 0.95d));
        statistic.setThe98thPercentile(getQuantilRuntime(runtimes, 0.98d));
        statistic.setThe99thPercentile(getQuantilRuntime(runtimes, 0.99d));
        statistic.setThe999thPercentile(getQuantilRuntime(runtimes, 0.999d));

        return statistic;

    }

    protected abstract T buildBaseStatistic();

    public double getQuantilRuntime(long[] runtimes, double quantile) {

        if (runtimes.length == 0) {
            return 0.0;
        }

        double pos = quantile * (runtimes.length + 1);

        if (pos < 1) {
            return runtimes[0];
        }

        if (pos >= runtimes.length) {
            return runtimes[runtimes.length - 1];
        }

        double lower = runtimes[( int ) pos - 1];
        double upper = runtimes[( int ) pos];

        return lower + (pos - floor(pos)) * (upper - lower);

    }

}
