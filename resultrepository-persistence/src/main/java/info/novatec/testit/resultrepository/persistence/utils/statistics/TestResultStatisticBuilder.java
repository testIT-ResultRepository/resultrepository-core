package info.novatec.testit.resultrepository.persistence.utils.statistics;

import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;


public class TestResultStatisticBuilder extends AbstractResultStatisticBuilder<TestResultStatistic> {

    private final Long testResultId;

    public TestResultStatisticBuilder(Long testResultId) {
        this.testResultId = testResultId;
    }

    @Override
    protected TestResultStatistic buildBaseStatistic() {
        TestResultStatistic statistic = new TestResultStatistic();
        statistic.setTestResultId(testResultId);
        return statistic;
    }

}
