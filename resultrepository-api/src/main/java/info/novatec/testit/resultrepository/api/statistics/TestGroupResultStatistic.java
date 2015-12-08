package info.novatec.testit.resultrepository.api.statistics;

import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;


/**
 * This {@link AbstractResultStatistic result statistic} contains statistical
 * information about all the {@link TestResult test results} of a specific
 * {@link TestGroupResult test group result}.
 *
 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class TestGroupResultStatistic extends AbstractResultStatistic {

    /**
     * The unique ID of the {@link TestGroupResult test group result} this
     * {@link TestGroupResultStatistic statistic} is referring to.
     */
    private Long testGroupResultId;

    public Long getTestGroupResultId() {
        return testGroupResultId;
    }

    public void setTestGroupResultId(Long testGroupResultId) {
        this.testGroupResultId = testGroupResultId;
    }

}
