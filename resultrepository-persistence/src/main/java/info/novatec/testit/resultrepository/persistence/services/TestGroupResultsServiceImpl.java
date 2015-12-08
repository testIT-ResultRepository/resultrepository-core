package info.novatec.testit.resultrepository.persistence.services;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNodeFactory;
import info.novatec.testit.resultrepository.server.api.TestGroupResultsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class TestGroupResultsServiceImpl implements TestGroupResultsService {

    @Autowired
    private TestGroupResultNodeFactory testGroupResultNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestGroupResultData findById(Long id) {
        return new TestGroupResultData(getRequiredTestGroupResultNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestGroupData findTestGroup(Long id) {
        return getRequiredTestGroupResultNode(id).getOptionalTestGroup()
            .map(testGroup -> new TestGroupData(testGroup))
            .orElse(null);
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public BuildData findBuild(Long id) {
        return getRequiredTestGroupResultNode(id).getOptionalBuild().map(build -> new BuildData(build)).orElse(null);
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TagData> findTags(Long id) {
        return getRequiredTestGroupResultNode(id).getTagsStream().map(tag -> new TagData(tag)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TagData> findTags(Long id, Predicate<Tag> filter) {
        return getRequiredTestGroupResultNode(id).getTagsStream()
            .filter(filter)
            .map(tag -> new TagData(tag))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestResultData> findTestResults(Long id) {
        return getRequiredTestGroupResultNode(id).getTestResultsStream()
            .map(result -> new TestResultData(result))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestResultData> findTestResults(Long id, Predicate<TestResult> filter) {
        return getRequiredTestGroupResultNode(id).getTestResultsStream()
            .filter(filter)
            .map(result -> new TestResultData(result))
            .collect(Collectors.toSet());
    }

    private TestGroupResultNode getRequiredTestGroupResultNode(Long id) {
        return testGroupResultNodeFactory.getFromGraph(id)
            .orElseThrow(() -> new DataNotFoundException(TestGroupResult.class, id));
    }

}
