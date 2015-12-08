package info.novatec.testit.resultrepository.persistence.utils.statistics;

import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;


public class TestGroupResultStatisticBuilder extends AbstractResultStatisticBuilder<TestGroupResultStatistic> {

    private final Long testGroupResultId;

    public TestGroupResultStatisticBuilder(Long testGroupResultId) {
        this.testGroupResultId = testGroupResultId;
    }

    @Override
    protected TestGroupResultStatistic buildBaseStatistic() {
        TestGroupResultStatistic statistic = new TestGroupResultStatistic();
        statistic.setTestGroupResultId(testGroupResultId);
        return statistic;
    }

}
