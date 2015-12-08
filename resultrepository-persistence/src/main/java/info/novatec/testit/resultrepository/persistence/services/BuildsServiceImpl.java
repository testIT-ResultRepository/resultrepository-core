package info.novatec.testit.resultrepository.persistence.services;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNodeFactory;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNodeFactory;
import info.novatec.testit.resultrepository.server.api.BuildsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class BuildsServiceImpl implements BuildsService {

    @Autowired
    private BuildNodeFactory buildNodeFactory;
    @Autowired
    private TagNodeFactory tagNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public BuildData persist(BuildData build) {

        BuildNode buildNode = buildNodeFactory.getOrCreateFromGraph(build.getBuildJob().getName(), build.getBuildNumber());

        if (build.hasCreationTimestamp()) {
            buildNode.setCreationTimestamp(build.getCreationTimestamp());
        }

        build.getCustomProperties().forEach((key, value) -> buildNode.setCustomProperty(key, value));
        build.getTags().forEach(tag -> buildNode.linkToTag(tagNodeFactory.getOrCreateFromGraph(tag.getValue())));

        return new BuildData(buildNode);

    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public BuildData findById(Long id) {
        return new BuildData(getRequiredBuildNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public BuildJobData findBuildJob(Long id) {
        return getRequiredBuildNode(id).getOptionalBuildJob().map(buildJob -> new BuildJobData(buildJob)).orElse(null);
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TagData> findTags(Long id) {
        return getRequiredBuildNode(id).getTagsStream().map(tag -> new TagData(tag)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TagData> findTags(Long id, Predicate<Tag> filter) {
        return getRequiredBuildNode(id).getTagsStream()
            .filter(filter)
            .map(tag -> new TagData(tag))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestGroupResultData> findTestGroupResults(Long id) {
        return getRequiredBuildNode(id).getTestGroupResultsStream()
            .map(result -> new TestGroupResultData(result))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestGroupResultData> findTestGroupResults(Long id, Predicate<TestGroupResult> filter) {
        return getRequiredBuildNode(id).getTestGroupResultsStream()
            .filter(filter)
            .map(result -> new TestGroupResultData(result))
            .collect(Collectors.toSet());
    }

    private BuildNode getRequiredBuildNode(Long id) {
        return buildNodeFactory.getFromGraph(id).orElseThrow(() -> new DataNotFoundException(Build.class, id));
    }

}
