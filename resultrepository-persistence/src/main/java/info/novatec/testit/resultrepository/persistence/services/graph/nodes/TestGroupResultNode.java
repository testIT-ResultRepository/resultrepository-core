package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#TEST_GROUP_RESULT} labeled node and
 * provides different operations for interacting with it as a test group result.
 */
public class TestGroupResultNode extends AbstractNodeWrapper implements TestGroupResult {

    /**
     * Creates a new {@linkplain TestGroupResultNode test group result}. Calling
     * this constructor will create a new {@linkplain Node node} in the graph.
     * If you want to wrap an existing {@linkplain Node node} as a
     * {@linkplain TestGroupResultNode test group result} use
     * {@linkplain #TestGroupResultNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     */
    protected TestGroupResultNode(GraphDatabaseService graphDb) {
        super(graphDb, Labels.TEST_GROUP_RESULT);
    }

    /**
     * Creates a new {@linkplain TestGroupResultNode test group result} by
     * wrapping the given {@linkplain Node node}. Validates if the given node
     * has the required label {@linkplain Labels#TEST_GROUP_RESULT}.
     *
     * @param node the node to wrap as a {@linkplain TestGroupResultNode test
     * group result}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_GROUP_RESULT} node
     */
    protected TestGroupResultNode(Node node) {
        super(node, Labels.TEST_GROUP_RESULT);
    }

    /**
     * Links this {@linkplain TestGroupResultNode test group result} with the
     * given {@linkplain TagNode tags}.
     *
     * @param tags the {@linkplain TagNode tags} to link to
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode linkToTags(TagNode... tags) {
        return linkToTags(Arrays.asList(tags));
    }

    /**
     * Links this {@linkplain TestGroupResultNode test group result} with the
     * given {@linkplain TagNode tags}.
     *
     * @param tags the {@linkplain TagNode tags} to link to
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode linkToTags(Collection<TagNode> tags) {
        for (TagNode tag : tags) {
            linkToTag(tag);
        }
        return this;
    }

    /**
     * Links this {@linkplain TestGroupResultNode test group result} to the
     * given {@linkplain TagNode tag}.
     *
     * @param tag the {@linkplain TagNode tag} to link to
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode linkToTag(TagNode tag) {
        createUniqueRelationshipTo(tag, RelationshipTypes.IS_USED_BY_TEST_GROUP_RESULT);
        return this;
    }

    /**
     * Returns all of the {@linkplain TagNode tags} this
     * {@linkplain TestGroupResultNode test group result} is linked with as a
     * {@linkplain Set set}.
     * <p>
     * This method exists to be compliant with the {@linkplain TestGroupResult}
     * interface. The preferred way to get the {@linkplain TagNode tags} of this
     * {@linkplain TestGroupResultNode test group result} is to use the
     * {@linkplain Stream stream} returning {@linkplain #getTagsStream()} method
     * instead.
     *
     * @return the tags of this {@linkplain TestGroupResultNode test group
     * result} as a Set
     */
    @Override
    public Set<TagNode> getTags() {
        return getTagsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all of the {@linkplain TagNode tags} this
     * {@linkplain TestGroupResultNode test group result} is linked with as a
     * {@linkplain Stream stream}.
     *
     * @return the tags of this {@linkplain TestGroupResultNode test group
     * result} as a {@linkplain Stream stream}
     */
    public Stream<TagNode> getTagsStream() {
        return getRelationships(RelationshipTypes.IS_USED_BY_TEST_GROUP_RESULT).map(
            rel -> TagNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

    /**
     * Links this {@linkplain TestGroupResultNode test group result} with the
     * given {@linkplain MetadataValueNode meta data value}.
     *
     * @param metadataValues the {@linkplain MetadataValueNode meta data values}
     * to link to
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode linkToMetadataValues(MetadataValueNode... metadataValues) {
        return linkToMetadataValues(Arrays.asList(metadataValues));
    }

    /**
     * Links this {@linkplain TestGroupResultNode test group result} with the
     * given {@linkplain MetadataValueNode meta data value}.
     *
     * @param metadataValues the {@linkplain MetadataValueNode meta data values}
     * to link to
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode linkToMetadataValues(Collection<MetadataValueNode> metadataValues) {
        for (MetadataValueNode metadataValue : metadataValues) {
            linkToMetadataValue(metadataValue);
        }
        return this;
    }

    /**
     * Links this {@linkplain TestGroupResultNode test group result} to the
     * given {@linkplain MetadataValueNode meta data value}.
     *
     * @param metadataValue the {@linkplain MetadataValueNode meta data value}
     * to link to
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode linkToMetadataValue(MetadataValueNode metadataValue) {
        createUniqueRelationshipTo(metadataValue, RelationshipTypes.IS_METADATA_VALUE_OF_TEST_GROUP_RESULT);
        return this;
    }

    /**
     * Returns all of the {@linkplain MetadataValueNode meta data values} this
     * {@linkplain TestGroupResultNode test group result} is linked with as a
     * {@linkplain Set set}.
     * <p>
     * This method exists to be compliant with the {@linkplain TestGroupResult}
     * interface. The preferred way to get the {@linkplain MetadataValueNode
     * meta data values} of this {@linkplain TestGroupResultNode test group
     * result} is to use the {@linkplain Stream stream} returning
     * {@linkplain #getMetadataValuesStream()} method instead.
     *
     * @return the meta data values of this {@linkplain TestGroupResultNode test
     * group result} as a Set
     */
    @Override
    public Set<MetadataValueNode> getMetadataValues() {
        return getMetadataValuesStream().collect(Collectors.toSet());
    }

    /**
     * Returns all of the {@linkplain MetadataValueNode meta data values} this
     * {@linkplain TestGroupResultNode test group result} is linked with as a
     * {@linkplain Stream stream}.
     *
     * @return the meta data values of this {@linkplain TestGroupResultNode test
     * group result} as a {@linkplain Stream stream}
     */
    public Stream<MetadataValueNode> getMetadataValuesStream() {
        return getRelationships(RelationshipTypes.IS_METADATA_VALUE_OF_TEST_GROUP_RESULT).map(
            rel -> MetadataValueNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

    /**
     * Adds the {@linkplain TestResultNode test results} to this
     * {@linkplain TestGroupResultNode test group result}. All relationship
     * pointer will be updated automatically.
     *
     * @param testResults the results to add
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode addTestResults(TestResultNode... testResults) {
        return addTestResults(Arrays.asList(testResults));
    }

    /**
     * Adds the {@linkplain TestResultNode test results} to this
     * {@linkplain TestGroupResultNode test group result}. All relationship
     * pointer will be updated automatically.
     *
     * @param testResults the results to add
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode addTestResults(Collection<TestResultNode> testResults) {
        for (TestResultNode testResult : testResults) {
            addTestResult(testResult);
        }
        return this;
    }

    /**
     * Adds the given {@linkplain TestResultNode test result} to this
     * {@linkplain TestGroupResultNode test group result}. All relationship
     * pointer will be updated automatically.
     *
     * @param testResult the {@linkplain TestResultNode test result} to add
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode addTestResult(TestResultNode testResult) {
        createUniqueRelationshipTo(testResult, RelationshipTypes.CONTAINS);
        return this;
    }

    /**
     * Returns all of the {@linkplain TestResultNode test results} this
     * {@linkplain TestGroupResultNode test group result} is linked with as a
     * {@linkplain Set set}.
     * <p>
     * The preferred way to do this would be to use the {@linkplain Stream
     * stream} returning {@linkplain #getTestResultsStream()} method instead.
     *
     * @return the test results of this test group result as a Set
     */
    @Override
    public Set<TestResultNode> getTestResults() {
        return getTestResultsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all of the {@linkplain TestResultNode test results} this
     * {@linkplain TestGroupResultNode test group result} is linked with as a
     * {@linkplain Stream stream}.
     *
     * @return the test results of this test group result as a stream
     */
    public Stream<TestResultNode> getTestResultsStream() {
        return getOutgoingRelationships(RelationshipTypes.CONTAINS).map(rel -> TestResultNodeFactory.wrap(rel.getEndNode()));
    }

    /**
     * Sets the {@linkplain TestGroupNode test group} to which this result
     * belongs. Only one relationship to any test group can exist at any given
     * moment, so by setting a test group any existing relationship will be
     * removed.
     *
     * @param testGroup the {@linkplain TestGroupNode test group} to set
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode setTestGroup(TestGroupNode testGroup) {
        getSingleOutgoingRelationship(RelationshipTypes.IS_RESULT_OF_TEST_GROUP).ifPresent(rel -> rel.delete());
        createUniqueRelationshipTo(testGroup, RelationshipTypes.IS_RESULT_OF_TEST_GROUP);
        return this;
    }

    @Override
    public TestGroupNode getTestGroup() {
        return getOptionalTestGroup().orElse(null);
    }

    /**
     * Returns the test group result's test group. This is essentially the same
     * operation as {@linkplain #getTestGroup()} with the difference of using
     * Java 8's Optional to avoid returning null.
     *
     * @return the test group as an {@linkplain Optional}
     */
    public Optional<TestGroupNode> getOptionalTestGroup() {
        return getSingleOutgoingRelationship(RelationshipTypes.IS_RESULT_OF_TEST_GROUP).map(
            rel -> TestGroupNodeFactory.wrap(rel.getEndNode()));
    }

    /**
     * Sets the {@linkplain BuildNode build} to which this result belongs. Only
     * one relationship to any build can exist at any given moment, so by
     * setting a build the original relationship will be removed.
     *
     * @param build the {@linkplain BuildNode build} to set
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestGroupResultNode setBuild(BuildNode build) {
        getSingleOutgoingRelationship(RelationshipTypes.IS_TEST_GROUP_RESULT_OF_BUILD).ifPresent(rel -> rel.delete());
        createUniqueRelationshipTo(build, RelationshipTypes.IS_TEST_GROUP_RESULT_OF_BUILD);
        return this;
    }

    @Override
    public BuildNode getBuild() {
        return getOptionalBuild().orElse(null);
    }

    /**
     * Returns the test group result's build. This is essentially the same
     * operation as {@linkplain #getBuild()} with the difference of using Java
     * 8's Optional to avoid returning null.
     *
     * @return the build as an {@linkplain Optional}
     */
    public Optional<BuildNode> getOptionalBuild() {
        return getSingleOutgoingRelationship(RelationshipTypes.IS_TEST_GROUP_RESULT_OF_BUILD).map(
            rel -> BuildNodeFactory.wrap(rel.getEndNode()));
    }

}
