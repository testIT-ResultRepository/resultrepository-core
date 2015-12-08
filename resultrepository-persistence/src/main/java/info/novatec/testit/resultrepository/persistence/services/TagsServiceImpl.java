package info.novatec.testit.resultrepository.persistence.services;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNodeFactory;
import info.novatec.testit.resultrepository.server.api.TagsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagNodeFactory tagNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TagData persist(TagData tag) {

        TagNode tagNode = tagNodeFactory.getOrCreateFromGraph(tag.getValue());

        if (tag.hasCreationTimestamp()) {
            tagNode.setCreationTimestamp(tag.getCreationTimestamp());
        }

        return new TagData(tagNode);

    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<TagData> findAll() {
        return tagNodeFactory.getAllFromGraph().map(tag -> new TagData(tag)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<TagData> findAll(Predicate<Tag> filter) {
        return tagNodeFactory.getAllFromGraph().filter(filter).map(tag -> new TagData(tag)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TagData findById(Long id) {
        return new TagData(getRequiredTagNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public TagData findByValue(String value) throws DataNotFoundException {
        return new TagData(getRequiredTagNode(value));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<BuildData> findBuilds(Long id) {
        return getRequiredTagNode(id).getBuildsStream().map(build -> new BuildData(build)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<BuildData> findBuilds(Long id, Predicate<Build> filter) {
        return getRequiredTagNode(id).getBuildsStream()
            .filter(filter)
            .map(build -> new BuildData(build))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestGroupResultData> findTestGroupResults(Long id) {
        return getRequiredTagNode(id).getTestGroupResultsStream()
            .map(result -> new TestGroupResultData(result))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestGroupResultData> findTestGroupResults(Long id, Predicate<TestGroupResult> filter) {
        return getRequiredTagNode(id).getTestGroupResultsStream()
            .filter(filter)
            .map(result -> new TestGroupResultData(result))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestResultData> findTestResults(Long id) {
        return getRequiredTagNode(id).getTestResultsStream()
            .map(result -> new TestResultData(result))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<TestResultData> findTestResults(Long id, Predicate<TestResult> filter) {
        return getRequiredTagNode(id).getTestResultsStream()
            .filter(filter)
            .map(result -> new TestResultData(result))
            .collect(Collectors.toSet());
    }

    private TagNode getRequiredTagNode(Long id) {
        return tagNodeFactory.getFromGraph(id).orElseThrow(() -> new DataNotFoundException(Tag.class, id));
    }

    private TagNode getRequiredTagNode(String value) {
        return tagNodeFactory.getFromGraph(value).orElseThrow(() -> new DataNotFoundException(Tag.class, value));
    }

}
