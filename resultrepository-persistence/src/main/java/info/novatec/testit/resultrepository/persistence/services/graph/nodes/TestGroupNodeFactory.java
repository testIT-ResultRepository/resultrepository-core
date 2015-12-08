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
public class TestGroupNodeFactory extends AbstractNodeFactory {

    /**
     * Returns the {@linkplain TestGroupNode test group} for the given name. If
     * no {@linkplain TestGroupNode test group} for that name exists, one will
     * be created.
     *
     * @param name the name of the {@linkplain TestGroupNode test group} to
     * return
     * @return the found or created {@linkplain TestGroupNode test group}
     */
    @Measured
    public TestGroupNode getOrCreateFromGraph(String name) {
        return getFromGraph(name).orElseGet(() -> {
            incrementTotalCreatedCounter();
            return new TestGroupNode(graph(), name);
        });
    }

    /**
     * Looks up a {@linkplain TestGroupNode test group} for the given name. It
     * is returned as an {@linkplain Optional} because there might not be a node
     * for that name.
     *
     * @param name the name of the {@linkplain TestGroupNode test group} to look
     * up
     * @return the optional {@linkplain TestGroupNode test group}
     */
    @Measured
    public Optional<TestGroupNode> getFromGraph(String name) {
        incrementTotalLookedUpByName();
        return getSingleNodeFromIndex(Labels.TEST_GROUP, TestGroupNode.PROPERTY_NAME, name).map(node -> {
            incrementTotalResolvedByName();
            return wrap(node);
        });
    }

    /**
     * Looks up a {@linkplain TestGroupNode test group} for the given ID. It is
     * returned as an {@linkplain Optional} because there might not be a node
     * for the given ID.
     *
     * @param id the ID of the {@linkplain TestGroupNode test group} to look up
     * @return the optional {@linkplain TestGroupNode test group}
     */
    @Measured
    public Optional<TestGroupNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            TestGroupNode testGroup = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(testGroup);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns all {@linkplain TestGroupNode test groups} from the given graph
     * as a sequential {@linkplain Stream}.
     *
     * @return a sequential {@linkplain Stream} of {@linkplain TestGroupNode
     * test groups}
     */
    public Stream<TestGroupNode> getAllFromGraph() {
        return getNodesForLabel(Labels.TEST_GROUP).map(node -> wrap(node));
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain TestGroupNode
     * test group} .Validates if the given node has the required label
     * {@linkplain Labels#TEST_GROUP}.
     *
     * @param node the {@linkplain Node node} to wrap as a {@linkplain TestNode
     * test}
     * @return the {@linkplain TestGroupNode test group}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_GROUP} node
     */
    public static TestGroupNode wrap(Node node) {
        return new TestGroupNode(node);
    }

}
