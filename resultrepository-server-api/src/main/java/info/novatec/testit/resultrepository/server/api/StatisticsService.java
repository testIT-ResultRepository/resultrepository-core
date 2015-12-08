package info.novatec.testit.resultrepository.server.api;

import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;
import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;
import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;


public interface StatisticsService {

    BuildResultStatistic calculateBuildResultStatistic(Long buildId);

    TestGroupResultStatistic calculateTestGroupResultStatistic(Long testGroupResultId);

    TestResultStatistic calculateTestResultStatistic(Long testResultId);

}
