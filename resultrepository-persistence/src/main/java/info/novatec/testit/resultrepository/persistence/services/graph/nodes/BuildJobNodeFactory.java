package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;
import java.util.stream.Stream;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;


@Service
public class BuildJobNodeFactory extends AbstractNodeFactory {

    /**
     * Returns the {@linkplain BuildJobNode build job} for the given name. If no
     * build job for that name exists, one will be created.
     *
     * @param name the name of the {@linkplain BuildJobNode build job} to return
     * @return the found or created {@linkplain BuildJobNode build job}
     */
    @Measured
    public BuildJobNode getOrCreateFromGraph(String name) {
        return getFromGraph(name).orElseGet(() -> {
            incrementTotalCreatedCounter();
            return new BuildJobNode(graph(), name);
        });
    }

    /**
     * Looks up a {@linkplain BuildJobNode build job} for the given name. It is
     * returned as an {@linkplain Optional} because there might not be a build
     * job node for that name.
     *
     * @param name the name of the {@linkplain BuildJobNode build job} to look
     * up
     * @return the optional {@linkplain BuildJobNode build job}
     */
    @Measured
    public Optional<BuildJobNode> getFromGraph(String name) {
        incrementTotalLookedUpByName();
        return getSingleNodeFromIndex(Labels.BUILD_JOB, BuildJobNode.PROPERTY_NAME, name).map(node -> {
            incrementTotalResolvedByName();
            return wrap(node);
        });
    }

    /**
     * Looks up a {@linkplain BuildJobNode build job} for the given ID. It is
     * returned as an {@linkplain Optional} because there might not be a node
     * for the given ID.
     *
     * @param id the ID of the {@linkplain BuildJobNode build job} to look up
     * @return the optional {@linkplain BuildJobNode build job}
     */
    @Measured
    public Optional<BuildJobNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            BuildJobNode buildJob = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(buildJob);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns all {@linkplain BuildJobNode build jobs} from the given graph as
     * a sequential {@linkplain Stream}.
     *
     * @return a sequential {@linkplain Stream} of {@linkplain BuildJobNode
     * build jobs}
     */
    public Stream<BuildJobNode> getAllFromGraph() {
        return getNodesForLabel(Labels.BUILD_JOB).map(node -> wrap(node));
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain BuildJobNode
     * build job} .Validates if the given node has the required label
     * {@linkplain Labels#BUILD_JOB}.
     *
     * @param node the {@linkplain Node node} to wrap as a
     * {@linkplain BuildJobNode build job}
     * @return the {@linkplain BuildJobNode build job}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#BUILD_JOB} node
     */
    public static BuildJobNode wrap(Node node) {
        return new BuildJobNode(node);
    }

}
