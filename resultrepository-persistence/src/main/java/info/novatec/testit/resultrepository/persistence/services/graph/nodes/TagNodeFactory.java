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
public class TagNodeFactory extends AbstractNodeFactory {

    /**
     * Returns the {@linkplain TagNode tag} for the given value. If no
     * {@linkplain TagNode tag} for that value exists, one will be created.
     *
     * @param value the value of the {@linkplain TagNode tag} to return
     * @return the found or created {@linkplain TagNode tag}
     */
    @Measured
    public TagNode getOrCreateFromGraph(String value) {
        return getFromGraph(value).orElseGet(() -> {
            incrementTotalCreatedCounter();
            return new TagNode(graph(), value);
        });
    }

    /**
     * Looks up a {@linkplain TagNode tag} for the given value. It is returned
     * as an {@linkplain Optional} because there might not be a node for that
     * value.
     *
     * @param value the value of the {@linkplain TagNode tag} to look up
     * @return the optional {@linkplain TagNode tag}
     */
    @Measured
    public Optional<TagNode> getFromGraph(String value) {
        incrementTotalLookedUpByName();
        return getSingleNodeFromIndex(Labels.TAG, TagNode.PROPERTY_VALUE, value).map(node -> {
            incrementTotalResolvedByName();
            return wrap(node);
        });
    }

    /**
     * Looks up a {@linkplain TagNode tag} for the given ID. It is returned as
     * an {@linkplain Optional} because there might not be a node for the given
     * ID.
     *
     * @param id the ID of the {@linkplain TagNode tag} to look up
     * @return the optional {@linkplain TagNode tag}
     */
    @Measured
    public Optional<TagNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            TagNode tag = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(tag);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns all {@linkplain TagNode tags} from the given graph as a
     * sequential {@linkplain Stream}.
     *
     * @return a sequential {@linkplain Stream} of {@linkplain TagNode tags}
     */
    public Stream<TagNode> getAllFromGraph() {
        return getNodesForLabel(Labels.TAG).map(node -> wrap(node));
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain TagNode tag}
     * .Validates if the given node has the required label
     * {@linkplain Labels#TAG}.
     *
     * @param node the {@linkplain Node node} to wrap as a {@linkplain TagNode
     * tag}
     * @return the {@linkplain TagNode tag}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TAG} node
     */
    public static TagNode wrap(Node node) {
        return new TagNode(node);
    }

}
