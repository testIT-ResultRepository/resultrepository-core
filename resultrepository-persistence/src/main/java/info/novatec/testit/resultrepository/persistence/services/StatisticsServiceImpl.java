package info.novatec.testit.resultrepository.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;
import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;
import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNodeFactory;
import info.novatec.testit.resultrepository.persistence.utils.statistics.BuildResultStatisticBuilder;
import info.novatec.testit.resultrepository.persistence.utils.statistics.TestGroupResultStatisticBuilder;
import info.novatec.testit.resultrepository.persistence.utils.statistics.TestResultStatisticBuilder;
import info.novatec.testit.resultrepository.server.api.StatisticsService;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private BuildNodeFactory buildNodeFactory;
    @Autowired
    private TestGroupResultNodeFactory testGroupResultNodeFactory;
    @Autowired
    private TestResultNodeFactory testResultNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public BuildResultStatistic calculateBuildResultStatistic(Long buildId) {

        BuildResultStatisticBuilder statisticBuilder = new BuildResultStatisticBuilder(buildId);

        BuildNode build = buildNodeFactory.getFromGraph(buildId).get();
        build.getTestGroupResultsStream()
            .flatMap(testGroupResult -> testGroupResult.getTestResults().stream())
            .forEach(testResult -> statisticBuilder.addResultData(testResult));
        return statisticBuilder.build();

    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestGroupResultStatistic calculateTestGroupResultStatistic(Long testGroupResultId) {

        TestGroupResultStatisticBuilder statisticBuilder = new TestGroupResultStatisticBuilder(testGroupResultId);

        TestGroupResultNode testGroupResult = testGroupResultNodeFactory.getFromGraph(testGroupResultId).get();
        testGroupResult.getTestResultsStream().forEach(testResult -> statisticBuilder.addResultData(testResult));
        return statisticBuilder.build();

    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestResultStatistic calculateTestResultStatistic(Long testResultId) {

        TestResultNode testResult = testResultNodeFactory.getFromGraph(testResultId).get();

        TestResultStatisticBuilder statisticBuilder = new TestResultStatisticBuilder(testResultId);
        statisticBuilder.addResultData(testResult);
        return statisticBuilder.build();

    }

}
