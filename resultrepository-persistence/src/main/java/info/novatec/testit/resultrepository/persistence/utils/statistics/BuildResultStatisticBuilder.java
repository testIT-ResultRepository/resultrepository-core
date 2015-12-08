package info.novatec.testit.resultrepository.persistence.utils.statistics;

import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;


public class BuildResultStatisticBuilder extends AbstractResultStatisticBuilder<BuildResultStatistic> {

    private final Long buildId;

    public BuildResultStatisticBuilder(Long buildId) {
        this.buildId = buildId;
    }

    @Override
    protected BuildResultStatistic buildBaseStatistic() {
        BuildResultStatistic statistic = new BuildResultStatistic();
        statistic.setBuildId(buildId);
        return statistic;
    }

}
