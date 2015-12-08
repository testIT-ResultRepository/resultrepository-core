package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;


@Service
public class TestGroupResultNodeFactory extends AbstractNodeFactory {

    /**
     * Creates a new {@linkplain TestGroupResultNode test group result}.
     *
     * @return the created {@linkplain TestGroupResultNode test group result}
     */
    @Measured
    public TestGroupResultNode createInGraph() {
        incrementTotalCreatedCounter();
        return new TestGroupResultNode(graph());
    }

    /**
     * Looks up a {@linkplain TestGroupResultNode test group result} for the
     * given ID. It is returned as an {@linkplain Optional} because there might
     * not be a node for the given ID.
     *
     * @param id the ID of the {@linkplain TestGroupResultNode test group
     * result} to look up
     * @return the optional {@linkplain TestGroupResultNode test group result}
     */
    @Measured
    public Optional<TestGroupResultNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            TestGroupResultNode testGroupResult = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(testGroupResult);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Wraps the given {@linkplain Node node} as a
     * {@linkplain TestGroupResultNode test group result} .Validates if the
     * given node has the required label {@linkplain Labels#TEST_GROUP_RESULT}.
     *
     * @param node the {@linkplain Node node} to wrap as a
     * {@linkplain TestGroupResultNode test group result}
     * @return the {@linkplain TestGroupResultNode test group result}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_GROUP_RESULT} node
     */
    public static TestGroupResultNode wrap(Node node) {
        return new TestGroupResultNode(node);
    }

}
