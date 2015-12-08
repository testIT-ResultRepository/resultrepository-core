package info.novatec.testit.resultrepository.persistence.services;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.TestGroup;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNodeFactory;
import info.novatec.testit.resultrepository.server.api.TestGroupsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class TestGroupsServiceImpl implements TestGroupsService {

    @Autowired
    private TestGroupNodeFactory testGroupNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestGroupData persist(TestGroupData testGroup) {

        TestGroupNode testGroupNode = testGroupNodeFactory.getOrCreateFromGraph(testGroup.getName());

        if (testGroup.hasCreationTimestamp()) {
            testGroupNode.setCreationTimestamp(testGroup.getCreationTimestamp());
        }

        return new TestGroupData(testGroupNode);

    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<TestGroupData> findAll() {
        return testGroupNodeFactory.getAllFromGraph()
            .map(testGroup -> new TestGroupData(testGroup))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<TestGroupData> findAll(Predicate<TestGroup> filter) {
        return testGroupNodeFactory.getAllFromGraph()
            .filter(filter)
            .map(testGroup -> new TestGroupData(testGroup))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestGroupData findById(Long id) {
        return new TestGroupData(getRequiredTestGroupNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestGroupResultData> findTestGroupResults(Long id) {
        return getRequiredTestGroupNode(id).getTestGroupResultsStream()
            .map(result -> new TestGroupResultData(result))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestGroupResultData> findTestGroupResults(Long id, Predicate<TestGroupResult> filter) {
        return getRequiredTestGroupNode(id).getTestGroupResultsStream()
            .filter(filter)
            .map(result -> new TestGroupResultData(result))
            .collect(Collectors.toSet());
    }

    private TestGroupNode getRequiredTestGroupNode(Long id) {
        return testGroupNodeFactory.getFromGraph(id).orElseThrow(() -> new DataNotFoundException(TestGroup.class, id));
    }

}
