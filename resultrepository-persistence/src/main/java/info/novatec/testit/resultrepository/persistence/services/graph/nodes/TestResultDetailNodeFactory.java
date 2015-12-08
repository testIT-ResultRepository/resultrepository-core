package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Optional;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;


@Service
public class TestResultDetailNodeFactory extends AbstractNodeFactory {

    /**
     * Creates a new {@linkplain TestResultDetailNode test result detail} .
     *
     * @return the created {@linkplain TestResultDetailNode test result detail}
     */
    @Measured
    public TestResultDetailNode createInGraph() {
        incrementTotalCreatedCounter();
        return new TestResultDetailNode(graph());
    }

    /**
     * Looks up a {@linkplain TestResultDetailNode test result detail} for the
     * given ID. It is returned as an {@linkplain Optional} because there might
     * not be a node for the given ID.
     *
     * @param id the ID of the {{@linkplain TestResultDetailNode test result
     * detail} to look up
     * @return the optional {@linkplain TestResultDetailNode test result detail}
     */
    @Measured
    public Optional<TestResultDetailNode> getFromGraph(long id) {
        incrementTotalLookedUpById();
        try {
            TestResultDetailNode testResultDetail = wrap(graph().getNodeById(id));
            incrementTotalResolvedById();
            return Optional.of(testResultDetail);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Wraps the given {@linkplain Node node} as a
     * {@linkplain TestResultDetailNode test result detail} .Validates if the
     * given node has the required label {@linkplain Labels#TEST_RESULT_DETAIL}.
     *
     * @param node the {@linkplain Node node} to wrap as a
     * {@linkplain TestResultDetailNode test result detail}
     * @return the {@linkplain TestResultDetailNode test result detail}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_RESULT_DETAIL} node
     */
    public static TestResultDetailNode wrap(Node node) {
        return new TestResultDetailNode(node);
    }

}
