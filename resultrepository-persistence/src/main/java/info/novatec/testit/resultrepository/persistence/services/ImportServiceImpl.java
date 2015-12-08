package info.novatec.testit.resultrepository.persistence.services;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.MetadataValueData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.MetadataValueNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.MetadataValueNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupResultNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultDetailNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestResultNodeFactory;
import info.novatec.testit.resultrepository.server.api.ImportService;


@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private BuildNodeFactory buildNodeFactory;
    @Autowired
    private TagNodeFactory tagNodeFactory;
    @Autowired
    private MetadataValueNodeFactory metadataValueNodeFactory;
    @Autowired
    private TestGroupNodeFactory testGroupFactory;
    @Autowired
    private TestGroupResultNodeFactory testGroupResultFactory;
    @Autowired
    private TestNodeFactory testFactory;
    @Autowired
    private TestResultNodeFactory testResultFactory;
    @Autowired
    private TestResultDetailNodeFactory testResultDetailFactory;

    /* mapping functions */

    private Function<TagData, TagNode> convertTagDataToNode = tag -> {
        String tagValue = tag.getValue();
        return tagNodeFactory.getOrCreateFromGraph(tagValue);
    };

    private Function<MetadataValueData, MetadataValueNode> convertMetadataValueDataToNode = metadataValueData -> {
        MetadataPath metadataPath = MetadataPath.from(metadataValueData);
        return metadataValueNodeFactory.getOrCreateFromGraph(metadataPath);
    };

    private Function<TestGroupData, TestGroupNode> convertTestGroupDataToNode = testGroupData -> {
        String testGroupName = testGroupData.getName();
        return testGroupFactory.getOrCreateFromGraph(testGroupName);
    };

    private Function<TestData, TestNode> convertTestDataToNode = testData -> {
        String testName = testData.getName();
        return testFactory.getOrCreateFromGraph(testName);
    };

    private Function<BuildData, BuildNode> convertBuildDataToNode = buildData -> {

        BuildJobData buildJob = buildData.getBuildJob();
        BuildNode buildNode = buildNodeFactory.getOrCreateFromGraph(buildJob.getName(), buildData.getBuildNumber());

        buildData.getTags().stream().map(convertTagDataToNode).forEach(tagNode -> buildNode.linkToTag(tagNode));
        buildData.getCustomProperties().forEach((key, value) -> buildNode.setCustomProperty(key, value));

        return buildNode;

    };

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.DEBUG)
    public void importResult(TestGroupResultData data) {
        importTestGroupResult(data);
    }

    /* test group results */

    protected TestGroupResultNode importTestGroupResult(TestGroupResultData data) {

        TestGroupResultNode node = testGroupResultFactory.createInGraph();

        setOptionalBuild(data, node);
        setOptionalTestGroup(data, node);
        setOptionalCreationTimestamp(data, node);

        addTags(data, node);
        addMetadataValues(data, node);
        addCustomProperties(data, node);

        addTestResults(data, node);

        return node;

    }

    private void setOptionalBuild(TestGroupResultData data, TestGroupResultNode node) {
        Optional.ofNullable(data.getBuild()).map(convertBuildDataToNode).ifPresent(buildNode -> {
            node.setBuild(buildNode);
        });
    }

    private void setOptionalTestGroup(TestGroupResultData data, TestGroupResultNode node) {
        Optional.ofNullable(data.getTestGroup()).map(convertTestGroupDataToNode).ifPresent(testGroupNode -> {
            node.setTestGroup(testGroupNode);
        });
    }

    private void setOptionalCreationTimestamp(TestGroupResultData data, TestGroupResultNode node) {
        Optional.ofNullable(data.getCreationTimestamp()).ifPresent(timestamp -> {
            node.setCreationTimestamp(timestamp);
        });
    }

    private void addTags(TestGroupResultData data, TestGroupResultNode node) {
        data.getTags().stream().map(convertTagDataToNode).forEach(tag -> {
            node.linkToTag(tag);
        });
    }

    private void addMetadataValues(TestGroupResultData data, TestGroupResultNode node) {
        data.getMetadataValues().stream().map(convertMetadataValueDataToNode).forEach(metadataValueNode -> {
            node.linkToMetadataValue(metadataValueNode);
        });
    }

    private void addCustomProperties(TestGroupResultData data, TestGroupResultNode node) {
        data.getCustomProperties().forEach((key, value) -> {
            node.setCustomProperty(key, value);
        });
    }

    private void addTestResults(TestGroupResultData data, TestGroupResultNode node) {
        data.getTestResults().stream().map(testResultData -> {
            return importTestResult(testResultData);
        }).forEach(testResultNode -> {
            node.addTestResult(testResultNode);
        });
    }

    /* test results */

    protected TestResultNode importTestResult(TestResultData data) {

        TestResultNode node = testResultFactory.createInGraph();

        setOptionalTest(data, node);
        setOptionalCreationTimestamp(data, node);
        setOptionalDuration(data, node);
        setOptionalStatus(data, node);

        addTags(data, node);
        addMetadataValues(data, node);
        addCustomProperties(data, node);

        addTestResultDetails(data, node);

        return node;

    }

    private void setOptionalTest(TestResultData data, TestResultNode node) {
        Optional.ofNullable(data.getTest()).map(convertTestDataToNode).ifPresent(testNode -> {
            node.setTest(testNode);
        });
    }

    private void setOptionalCreationTimestamp(TestResultData data, TestResultNode node) {
        Optional.ofNullable(data.getCreationTimestamp()).ifPresent(timestamp -> {
            node.setCreationTimestamp(timestamp);
        });
    }

    private void setOptionalDuration(TestResultData data, TestResultNode node) {
        Optional.ofNullable(data.getDuration()).ifPresent(duration -> {
            node.setDuration(duration);
        });
    }

    private void setOptionalStatus(TestResultData data, TestResultNode node) {
        Optional.ofNullable(data.getStatus()).ifPresent(status -> {
            node.setStatus(status);
        });
    }

    private void addTags(TestResultData data, TestResultNode node) {
        data.getTags().stream().map(convertTagDataToNode).forEach(tag -> {
            node.linkToTag(tag);
        });
    }

    private void addMetadataValues(TestResultData data, TestResultNode node) {
        data.getMetadataValues().stream().map(convertMetadataValueDataToNode).forEach(metadataValueNode -> {
            node.linkToMetadataValue(metadataValueNode);
        });
    }

    private void addCustomProperties(TestResultData data, TestResultNode node) {
        data.getCustomProperties().forEach((key, value) -> {
            node.setCustomProperty(key, value);
        });
    }

    private void addTestResultDetails(TestResultData data, TestResultNode node) {
        data.getTestResultDetails().stream().map(testResultDetailData -> {
            return importTestResultDetail(testResultDetailData);
        }).forEach(testResultDetailNode -> {
            node.addTestResultDetail(testResultDetailNode);
        });
    }

    /* test result details */

    protected TestResultDetailNode importTestResultDetail(TestResultDetailData data) {

        TestResultDetailNode node = testResultDetailFactory.createInGraph();

        setOptionalCreationTimestamp(data, node);
        setOptionalMessage(data, node);
        setOptionalType(data, node);

        return node;

    }

    private void setOptionalCreationTimestamp(TestResultDetailData data, TestResultDetailNode node) {
        Optional.ofNullable(data.getCreationTimestamp()).ifPresent(timestamp -> {
            node.setCreationTimestamp(timestamp);
        });
    }

    private void setOptionalMessage(TestResultDetailData data, TestResultDetailNode node) {
        Optional.ofNullable(data.getMessage()).ifPresent(message -> {
            node.setMessage(message);
        });
    }

    private void setOptionalType(TestResultDetailData data, TestResultDetailNode node) {
        Optional.ofNullable(data.getType()).ifPresent(type -> {
            node.setType(type);
        });
    }

}
