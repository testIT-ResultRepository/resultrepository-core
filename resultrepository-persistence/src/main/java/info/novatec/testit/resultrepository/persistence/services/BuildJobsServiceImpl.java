package info.novatec.testit.resultrepository.persistence.services;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.BuildJob;
import info.novatec.testit.resultrepository.logging.api.LogLevel;
import info.novatec.testit.resultrepository.logging.api.Logged;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.annotations.Transacted;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildJobNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildJobNodeFactory;
import info.novatec.testit.resultrepository.server.api.BuildJobsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@Service
public class BuildJobsServiceImpl implements BuildJobsService {

    @Autowired
    private BuildJobNodeFactory buildJobNodeFactory;

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public BuildJobData persist(BuildJobData buildJob) {

        BuildJobNode buildJobNode = buildJobNodeFactory.getOrCreateFromGraph(buildJob.getName());

        if (buildJob.hasCreationTimestamp()) {
            buildJobNode.setCreationTimestamp(buildJob.getCreationTimestamp());
        }

        return new BuildJobData(buildJobNode);

    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<BuildJobData> findAll() {
        return buildJobNodeFactory.getAllFromGraph().map(buildJob -> new BuildJobData(buildJob)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE)
    public Set<BuildJobData> findAll(Predicate<BuildJob> filter) {
        return buildJobNodeFactory.getAllFromGraph()
            .filter(filter)
            .map(buildJob -> new BuildJobData(buildJob))
            .collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public BuildJobData findById(Long id) {
        return new BuildJobData(getRequiredBuildJobNode(id));
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<BuildData> findBuilds(Long id) {
        return getRequiredBuildJobNode(id).getBuildsStream().map(build -> new BuildData(build)).collect(Collectors.toSet());
    }

    @Override
    @Measured
    @Transacted
    @Logged(logLevel = LogLevel.TRACE, printArguments = true)
    public Set<BuildData> findBuilds(Long id, Predicate<Build> filter) {
        return getRequiredBuildJobNode(id).getBuildsStream()
            .filter(filter)
            .map(build -> new BuildData(build))
            .collect(Collectors.toSet());
    }

    private BuildJobNode getRequiredBuildJobNode(Long id) {
        return buildJobNodeFactory.getFromGraph(id).orElseThrow(() -> new DataNotFoundException(BuildJob.class, id));
    }

}
