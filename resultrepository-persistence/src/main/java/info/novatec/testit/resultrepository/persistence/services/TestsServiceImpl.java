package info.novatec.testit.resultrepository.persistence.services;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Test;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNodeFactory;
import info.novatec.testit.resultrepository.server.api.TestsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class TestsServiceImpl implements TestsService {

    @Autowired
    private TestNodeFactory testNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestData persist(TestData test) {

        TestNode testNode = testNodeFactory.getOrCreateFromGraph(test.getName());

        if (test.hasCreationTimestamp()) {
            testNode.setCreationTimestamp(test.getCreationTimestamp());
        }

        return new TestData(testNode);

    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<TestData> findAll() {
        return testNodeFactory.getAllFromGraph().map(test -> new TestData(test)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<TestData> findAll(Predicate<Test> filter) {
        return testNodeFactory.getAllFromGraph().filter(filter).map(test -> new TestData(test)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestData findById(Long id) {
        return new TestData(getRequiredTestNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestResultData> findTestResults(Long id) {
        return getRequiredTestNode(id).getTestResultsStream()
            .map(result -> new TestResultData(result))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestResultData> findTestResults(Long id, Predicate<TestResult> filter) {
        return getRequiredTestNode(id).getTestResultsStream()
            .filter(filter)
            .map(result -> new TestResultData(result))
            .collect(Collectors.toSet());
    }

    private TestNode getRequiredTestNode(Long id) {
        return testNodeFactory.getFromGraph(id).orElseThrow(() -> new DataNotFoundException(Test.class, id));
    }

}
