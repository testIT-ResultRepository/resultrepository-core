package info.novatec.testit.resultrepository.api.statistics;

import info.novatec.testit.resultrepository.api.interfaces.TestResult;


/**
 * This {@link AbstractResultStatistic result statistic} contains statistical
 * information about a single {@link TestResult test result}.
 *
 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class TestResultStatistic extends AbstractResultStatistic {

    /**
     * The unique ID of the {@link TestResult test result} this
     * {@link TestResultStatistic statistic} is referring to.
     */
    private Long testResultId;

    public Long getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Long testResultId) {
        this.testResultId = testResultId;
    }

}
