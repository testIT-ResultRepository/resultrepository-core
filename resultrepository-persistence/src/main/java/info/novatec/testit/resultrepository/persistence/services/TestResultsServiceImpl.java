package info.novatec.testit.resultrepository.persistence.services;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;
import info.novatec.testit.resultrepository.api.utils.Comparators;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNodeFactory;
import info.novatec.testit.resultrepository.server.api.TestResultsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class TestResultsServiceImpl implements TestResultsService {

    @Autowired
    private TestResultNodeFactory testResultNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestResultData findById(Long id) {
        return new TestResultData(getRequiredTestResultNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestData findTest(Long id) {
        return getRequiredTestResultNode(id).getOptionalTest().map(test -> new TestData(test)).orElse(null);
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestGroupResultData findTestGroupResult(Long id) {
        return getRequiredTestResultNode(id).getTestGroupResult()
            .map(testGroupResult -> new TestGroupResultData(testGroupResult))
            .orElse(null);
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TagData> findTags(Long id) {
        return getRequiredTestResultNode(id).getTagsStream().map(tag -> new TagData(tag)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TagData> findTags(Long id, Predicate<Tag> filter) {
        return getRequiredTestResultNode(id).getTagsStream()
            .filter(filter)
            .map(tag -> new TagData(tag))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public List<TestResultDetailData> findTestResultDetails(Long id) {
        return getRequiredTestResultNode(id).getTestResultDetailsStream()
            .map(detail -> new TestResultDetailData(detail))
            .sorted(Comparators.TIMESTAMP_ASC)
            .collect(Collectors.toList());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public List<TestResultDetailData> findTestResultDetails(Long id, Predicate<TestResultDetail> filter) {
        return getRequiredTestResultNode(id).getTestResultDetailsStream()
            .filter(filter)
            .map(detail -> new TestResultDetailData(detail))
            .sorted(Comparators.TIMESTAMP_ASC)
            .collect(Collectors.toList());
    }

    private TestResultNode getRequiredTestResultNode(Long id) {
        return testResultNodeFactory.getFromGraph(id).orElseThrow(() -> new DataNotFoundException(TestResult.class, id));
    }

}
