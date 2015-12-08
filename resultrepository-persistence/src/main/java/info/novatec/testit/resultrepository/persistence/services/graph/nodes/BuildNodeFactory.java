package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


@Service
public class BuildNodeFactory extends AbstractNodeFactory {

    @Autowired
    private BuildJobNodeFactory buildJobNodeFactory;

    /**
     * Returns the {@linkplain BuildNode build} for the given name and number.
     * If no build job for that name exists, one will be created. If no build
     * for the name and number exists it will be created as well.
     *
     * @param buildJobName the build job's name of the {@linkplain BuildNode
     * build} to return
     * @param buildNumber the number of the {@linkplain BuildNode build} to
     * return
     * @return the found or created {@linkplain BuildNode build}
     */
    @Measured
    public BuildNode getOrCreateFromGraph(String buildJobName, int buildNumber) {
        return getFromGraph(buildJobName, buildNumber).orElseGet(() -> {

            incrementTotalCreatedCounter();

            BuildJobNode buildJob = buildJobNodeFactory.getOrCreateFromGraph(buildJobName);
            BuildNode build = new BuildNode(graph(), buildJobName, buildNumber);
            setBuildJob(build, buildJob);
            return build;

        });
    }

    private void setBuildJob(BuildNode build, BuildJobNode buildJob) {

        Optional<BuildNode> currentLastBuild = buildJob.getLastBuild();
        if (currentLastBuild.isPresent()) {
            buildJob.deleteAllOutgoingRelationshipsOfType(RelationshipTypes.LAST_BUILD);
            currentLastBuild.get().createUniqueRelationshipTo(build, RelationshipTypes.NEXT_BUILD);
        } else {
            buildJob.createUniqueRelationshipTo(build, RelationshipTypes.FIRST_BUILD);
        }

        buildJob.createUniqueRelationshipTo(build, RelationshipTypes.LAST_BUILD);
        buildJob.createUniqueRelationshipTo(build, RelationshipTypes.CONTAINS);

    }

    /**
     * Looks up a {@linkplain BuildNode build} for the given name and number. It
     * is returned as an {@linkplain Optional} because there might not be a
     * build node for that name and number.
     *
     * @param buildName the name of the {@linkplain BuildNode build's} build job
     * to look up
     * @param buildNumber the number of the {@linkplain BuildNode build} to look
     * up
     * @return the optional {@linkplain BuildNode build}
     */
    @Measured
    public Optional<BuildNode> getFromGraph(String buildName, int buildNumber) {
        incrementTotalLookedUpByName();
        String identifier = getIdentifier(buildName, buildNumber);
        return getSingleNodeFromIndex(Labels.BUILD, BuildNode.PROPERTY_IDENTIFIER, identifier).map(node -> {
            incrementTotalResolvedByName();
            return new BuildNode(node);
        });
    }

    /**
     * Looks up a {@linkplain BuildNode build} for the given ID. It is returned
     * as an {@linkplain Optional} because there might not be a node for the
     * given ID.
     *
     * @param id the ID of the {@linkplain BuildNode build} to look up
     * @return the optional {@linkplain BuildNode build}
     */
    @Measured
    public Optional<BuildNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            BuildNode build = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(build);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain BuildNode build}
     * .Validates if the given node has the required label
     * {@linkplain Labels#BUILD}.
     *
     * @param node the {@linkplain Node node} to wrap as a {@linkplain BuildNode
     * build}
     * @return the {@linkplain BuildNode build}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#BUILD} node
     */
    public static BuildNode wrap(Node node) {
        return new BuildNode(node);
    }

    public static String getIdentifier(String buildName, int buildNumber) {
        return buildName + ":" + buildNumber;
    }

}
