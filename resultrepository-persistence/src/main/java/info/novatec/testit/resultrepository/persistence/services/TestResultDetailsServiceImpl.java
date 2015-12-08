package info.novatec.testit.resultrepository.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNodeFactory;
import info.novatec.testit.resultrepository.server.api.TestResultDetailsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class TestResultDetailsServiceImpl implements TestResultDetailsService {

    @Autowired
    private TestResultDetailNodeFactory testResultDetailNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestResultDetailData findById(Long id) {
        return new TestResultDetailData(getRequiredTestResultDetailNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TestResultData findTestResult(Long id) {
        return getRequiredTestResultDetailNode(id).getTestResult()
            .map(testResult -> new TestResultData(testResult))
            .orElse(null);
    }

    private TestResultDetailNode getRequiredTestResultDetailNode(Long id) {
        return testResultDetailNodeFactory.getFromGraph(id)
            .orElseThrow(() -> new DataNotFoundException(TestResultDetail.class, id));
    }

}
