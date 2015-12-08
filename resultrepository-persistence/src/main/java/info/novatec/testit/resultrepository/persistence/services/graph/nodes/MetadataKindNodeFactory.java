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
public class MetadataKindNodeFactory extends AbstractNodeFactory {

    /**
     * Returns the {@linkplain MetadataKindNode meta data kind} for the given
     * name. If no meta data kind for that name exists, one will be created.
     *
     * @param name the name of the {@linkplain MetadataKindNode meta data kind}
     * to return
     * @return the found or created {@linkplain MetadataKindNode meta data kind}
     */
    @Measured
    public MetadataKindNode getOrCreateFromGraph(String name) {
        return getFromGraph(name).orElseGet(() -> {
            incrementTotalCreatedCounter();
            return new MetadataKindNode(graph(), name);
        });
    }

    /**
     * Looks up a {@linkplain MetadataKindNode meta data kind} for the given
     * name. It is returned as an {@linkplain Optional} because there might not
     * be a node for that name.
     *
     * @param name the name of the {@linkplain MetadataKindNode meta data kind}
     * to look up
     * @return the optional {@linkplain MetadataKindNode meta data kind}
     */
    @Measured
    public Optional<MetadataKindNode> getFromGraph(String name) {
        incrementTotalLookedUpByName();
        return getSingleNodeFromIndex(Labels.METADATA_KIND, MetadataKindNode.PROPERTY_NAME, name).map(node -> {
            incrementTotalResolvedByName();
            return wrap(node);
        });
    }

    /**
     * Looks up a {@linkplain MetadataKindNode meta data kind} for the given ID.
     * It is returned as an {@linkplain Optional} because there might not be a
     * node for the given ID.
     *
     * @param id the ID of the {@linkplain MetadataKindNode meta data kind} to
     * look up
     * @return the optional {@linkplain MetadataKindNode meta data kind}
     */
    @Measured
    public Optional<MetadataKindNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            MetadataKindNode metadataKind = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(metadataKind);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns all {@linkplain MetadataKindNode meta data kinds} from the given
     * graph as a sequential {@linkplain Stream}.
     *
     * @return a sequential {@linkplain Stream} of {@linkplain MetadataKindNode
     * meta data kinds}
     */
    public Stream<MetadataKindNode> getAllFromGraph() {
        return getNodesForLabel(Labels.METADATA_KIND).map(node -> wrap(node));
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain MetadataKindNode
     * meta data kind} .Validates if the given node has the required label
     * {@linkplain Labels#METADATA_KIND}.
     *
     * @param node the {@linkplain Node node} to wrap as a
     * {@linkplain MetadataKindNode meta data kind}
     * @return the {@linkplain MetadataKindNode meta data kind}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#METADATA_KIND} node
     */
    public static MetadataKindNode wrap(Node node) {
        return new MetadataKindNode(node);
    }

}
