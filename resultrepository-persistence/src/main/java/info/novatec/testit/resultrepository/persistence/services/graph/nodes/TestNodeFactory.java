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
public class TestNodeFactory extends AbstractNodeFactory {

    /**
     * Returns the {@linkplain TestNode test} for the given name. If no
     * {@linkplain TestNode test} for that name exists, one will be created.
     *
     * @param name the name of the {@linkplain TestNode test} to return
     * @return the found or created {@linkplain TestNode test}
     */
    @Measured
    public TestNode getOrCreateFromGraph(String name) {
        return getFromGraph(name).orElseGet(() -> {
            incrementTotalCreatedCounter();
            return new TestNode(graph(), name);
        });
    }

    /**
     * Looks up a {@linkplain TestNode test} for the given name. It is returned
     * as an {@linkplain Optional} because there might not be a node for that
     * name.
     *
     * @param name the name of the {@linkplain TestNode test} to look up
     * @return the optional {@linkplain TestNode test}
     */
    @Measured
    public Optional<TestNode> getFromGraph(String name) {
        incrementTotalLookedUpByName();
        return getSingleNodeFromIndex(Labels.TEST, TestNode.PROPERTY_NAME, name).map(node -> {
            incrementTotalResolvedByName();
            return wrap(node);
        });
    }

    /**
     * Looks up a {@linkplain TestNode test} for the given ID. It is returned as
     * an {@linkplain Optional} because there might not be a node for the given
     * ID.
     *
     * @param id the ID of the {@linkplain TestNode test} to look up
     * @return the optional {@linkplain TestNode test}
     */
    @Measured
    public Optional<TestNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            TestNode test = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(test);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns all {@linkplain TestNode tests} from the given graph as a
     * sequential {@linkplain Stream}.
     *
     * @return a sequential {@linkplain Stream} of {@linkplain TestNode tests}
     */
    public Stream<TestNode> getAllFromGraph() {
        return getNodesForLabel(Labels.TEST).map(node -> wrap(node));
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain TestNode test}
     * .Validates if the given node has the required label
     * {@linkplain Labels#TEST}.
     *
     * @param node the {@linkplain Node node} to wrap as a {@linkplain TestNode
     * test}
     * @return the {@linkplain TestNode test}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST} node
     */
    public static TestNode wrap(Node node) {
        return new TestNode(node);
    }

}
