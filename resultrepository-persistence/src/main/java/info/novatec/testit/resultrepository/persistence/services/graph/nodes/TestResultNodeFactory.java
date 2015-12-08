package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;


@Service
public class TestResultNodeFactory extends AbstractNodeFactory {

    /**
     * Creates a new {@linkplain TestResultNode test result}.
     *
     * @return the created {@linkplain TestResultNode test result}
     */
    @Measured
    public TestResultNode createInGraph() {
        incrementTotalCreatedCounter();
        return new TestResultNode(graph());
    }

    /**
     * Looks up a {@linkplain TestResultNode test result} for the given ID. It
     * is returned as an {@linkplain Optional} because there might not be a node
     * for the given ID.
     *
     * @param id the ID of the {@linkplain TestResultNode test result} to look
     * up
     * @return the optional {@linkplain TestResultNode test result}
     */
    @Measured
    public Optional<TestResultNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            TestResultNode testResult = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(testResult);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain TestResultNode
     * test result} .Validates if the given node has the required label
     * {@linkplain Labels#TEST_RESULT}.
     *
     * @param node the {@linkplain Node node} to wrap as a
     * {@linkplain TestResultNode test result}
     * @return the {@linkplain TestResultNode test result}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_RESULT} node
     */
    public static TestResultNode wrap(Node node) {
        return new TestResultNode(node);
    }

}
