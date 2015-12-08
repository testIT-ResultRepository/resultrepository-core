package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.api.utils.Comparators;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#TEST_RESULT} labeled node and provides
 * different operations for interacting with it as a test result.
 */
public class TestResultNode extends AbstractNodeWrapper implements TestResult {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_DURATION = "duration";
    public static final String PROPERTY_STATUS = "status";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain TestResultNode test result}. Calling this
     * constructor will create a new {@linkplain Node node} in the graph. If you
     * want to wrap an existing {@linkplain Node node} as a
     * {@linkplain TestResultNode test result} use
     * {@linkplain #TestResultNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     */
    protected TestResultNode(GraphDatabaseService graphDb) {
        super(graphDb, Labels.TEST_RESULT);
    }

    /**
     * Creates a new {@linkplain TestResultNode test result} by wrapping the
     * given {@linkplain Node node}. Validates if the given node has the
     * required label {@linkplain Labels#TEST_RESULT}.
     *
     * @param node the node to wrap as a {@linkplain TestResultNode test result}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#TEST_RESULT} node
     */
    protected TestResultNode(Node node) {
        super(node, Labels.TEST_RESULT);
    }

    /**
     * Sets the duration of this {@linkplain TestResultNode test result}. The
     * duration is the amount of time the test took to complete in milliseconds.
     * Negative values will be automatically set to 0.
     *
     * @param duration the duration in milliseconds
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode setDuration(long duration) {
        setProperty(PROPERTY_DURATION, normalizeDuration(duration));
        return this;
    }

    private long normalizeDuration(long value) {
        return value > 0 ? value : 0;
    }

    /**
     * Returns the duration of this {@linkplain TestResultNode test result}. The
     * duration is the amount of time the test took to complete in milliseconds.
     * If there is no duration set 0 is returned.
     *
     * @return the duration
     */
    @Override
    public Long getDuration() {
        return getLongProperty(PROPERTY_DURATION, 0L);
    }

    /**
     * Sets the {@linkplain ResultStatus status} of this
     * {@linkplain TestResultNode test result}. If null is given as a type it
     * defaults to {@linkplain ResultStatus#UNKNOWN}.
     *
     * @param status the {@linkplain ResultStatus status} to set
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode setStatus(ResultStatus status) {
        setProperty(PROPERTY_STATUS, status != null ? status.name() : String.valueOf(ResultStatus.UNKNOWN));
        return this;
    }

    /**
     * Returns the {@linkplain ResultStatus status} of this
     * {@linkplain TestResultNode test result}. If no status information is
     * available it will default to {@linkplain ResultStatus#UNKNOWN}.
     *
     * @return the {@linkplain ResultStatus status}
     */
    @Override
    public ResultStatus getStatus() {
        return getStringProperty(PROPERTY_STATUS).map(name -> ResultStatus.valueOf(name)).orElse(ResultStatus.UNKNOWN);
    }

    /**
     * Links this {@linkplain TestResultNode test result} with the given
     * {@linkplain TagNode tags}.
     *
     * @param tags the {@linkplain TagNode tags} to link to
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode linkToTags(TagNode... tags) {
        return linkToTags(Arrays.asList(tags));
    }

    /**
     * Links this {@linkplain TestResultNode test result} with the given
     * {@linkplain TagNode tags}.
     *
     * @param tags the {@linkplain TagNode tags} to link to
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode linkToTags(Collection<TagNode> tags) {
        for (TagNode tag : tags) {
            linkToTag(tag);
        }
        return this;
    }

    /**
     * Links this {@linkplain TestResultNode test result} to the given
     * {@linkplain TagNode tag}.
     *
     * @param tag the {@linkplain TagNode tag} to link to
     * @return the same {@linkplain TestGroupResultNode test group result}
     * instance to support fluent API operations
     */
    public TestResultNode linkToTag(TagNode tag) {
        createUniqueRelationshipTo(tag, RelationshipTypes.IS_USED_BY_TEST_RESULT);
        return this;
    }

    /**
     * Returns all of the {@linkplain TagNode tags} this
     * {@linkplain TestResultNode test result} is linked with as a
     * {@linkplain Set set}.
     * <p>
     * This method exists to be compliant with the {@linkplain TestResult}
     * interface. The preferred way to get the {@linkplain TagNode tags} of this
     * {@linkplain TestResultNode test result} is to use the {@linkplain Stream
     * stream} returning {@linkplain #getTagsStream()} method instead.
     *
     * @return the tags of this {@linkplain TestResultNode test result} as a Set
     */
    @Override
    public Set<TagNode> getTags() {
        return getTagsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all of the {@linkplain TagNode tags} this
     * {@linkplain TestResultNode test result} is linked with as a
     * {@linkplain Stream stream}.
     *
     * @return the tags of this {@linkplain TestResultNode test result} as a
     * {@linkplain Stream stream}
     */
    public Stream<TagNode> getTagsStream() {
        return getRelationships(RelationshipTypes.IS_USED_BY_TEST_RESULT).map(
            rel -> TagNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

    /**
     * Links this {@linkplain TestResultNode test result} with the given
     * {@linkplain MetadataValueNode meta data value}.
     *
     * @param metadataValues the {@linkplain MetadataValueNode meta data values}
     * to link to
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode linkToMetadataValues(MetadataValueNode... metadataValues) {
        return linkToMetadataValues(Arrays.asList(metadataValues));
    }

    /**
     * Links this {@linkplain TestResultNode test result} with the given
     * {@linkplain MetadataValueNode meta data value}.
     *
     * @param metadataValues the {@linkplain MetadataValueNode meta data values}
     * to link to
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode linkToMetadataValues(Collection<MetadataValueNode> metadataValues) {
        for (MetadataValueNode metadataValue : metadataValues) {
            linkToMetadataValue(metadataValue);
        }
        return this;
    }

    /**
     * Links this {@linkplain TestResultNode test result} to the given
     * {@linkplain MetadataValueNode meta data value}.
     *
     * @param metadataValue the {@linkplain MetadataValueNode meta data value}
     * to link to
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode linkToMetadataValue(MetadataValueNode metadataValue) {
        createUniqueRelationshipTo(metadataValue, RelationshipTypes.IS_METADATA_VALUE_OF_TEST_RESULT);
        return this;
    }

    /**
     * Returns all of the {@linkplain MetadataValueNode meta data values} this
     * {@linkplain TestResultNode test result} is linked with as a
     * {@linkplain Set set}.
     * <p>
     * This method exists to be compliant with the {@linkplain TestResult}
     * interface. The preferred way to get the {@linkplain MetadataValueNode
     * meta data values} of this {@linkplain TestResultNode test result} is to
     * use the {@linkplain Stream stream} returning
     * {@linkplain #getMetadataValuesStream()} method instead.
     *
     * @return the meta data values of this {@linkplain TestResultNode test
     * result} as a Set
     */
    @Override
    public Set<MetadataValueNode> getMetadataValues() {
        return getMetadataValuesStream().collect(Collectors.toSet());
    }

    /**
     * Returns all of the {@linkplain MetadataValueNode meta data values} this
     * {@linkplain TestResultNode test result} is linked with as a
     * {@linkplain Stream stream}.
     *
     * @return the meta data values of this {@linkplain TestResultNode test
     * result} as a {@linkplain Stream stream}
     */
    public Stream<MetadataValueNode> getMetadataValuesStream() {
        return getRelationships(RelationshipTypes.IS_METADATA_VALUE_OF_TEST_RESULT).map(
            rel -> MetadataValueNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

    /**
     * Adds the {@linkplain TestResultDetailNode test result details} to this
     * {@linkplain TestResultNode test result}. All relationship pointer will be
     * updated automatically.
     *
     * @param testResultDetails the details to add
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode addTestResultDetails(TestResultDetailNode... testResultDetails) {
        return addTestResultDetails(Arrays.asList(testResultDetails));
    }

    /**
     * Adds the {@linkplain TestResultDetailNode test result details} to this
     * {@linkplain TestResultNode test result}. All relationship pointer will be
     * updated automatically.
     *
     * @param testResultDetails the details to add
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode addTestResultDetails(Collection<TestResultDetailNode> testResultDetails) {
        for (TestResultDetailNode testResultDetail : testResultDetails) {
            addTestResultDetail(testResultDetail);
        }
        return this;
    }

    /**
     * Adds the {@linkplain TestResultDetailNode test result detail} to this
     * {@linkplain TestResultNode test result}. All relationship pointer will be
     * updated automatically.
     *
     * @param testResultDetail the detail to add
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode addTestResultDetail(TestResultDetailNode testResultDetail) {
        createUniqueRelationshipTo(testResultDetail, RelationshipTypes.CONTAINS);
        return this;
    }

    /**
     * Returns all of the {@linkplain TestResultDetailNode test result details}
     * this {@linkplain TestResultNode test result} is linked with as a
     * {@linkplain List list}. This list is ordered by the included nodes
     * creation timestamp.
     * <p>
     * The preferred way to do this would be to use the {@linkplain Stream
     * stream} returning {@linkplain #getTestResultDetailsStream()} method
     * instead.
     *
     * @return the test result details of this test result
     */
    @Override
    public List<TestResultDetailNode> getTestResultDetails() {
        return getTestResultDetailsStream().sorted(Comparators.TIMESTAMP_ASC).collect(Collectors.toList());
    }

    /**
     * Returns all of the {@linkplain TestResultDetailNode test result detailss}
     * this {@linkplain TestResultNode test result} is linked with as a
     * {@linkplain Stream stream}.
     *
     * @return the test result details of this test result as a stream
     */
    public Stream<TestResultDetailNode> getTestResultDetailsStream() {
        return getOutgoingRelationships(RelationshipTypes.CONTAINS).map(
            rel -> TestResultDetailNodeFactory.wrap(rel.getEndNode()));
    }

    /**
     * Sets the {@linkplain TestNode test} to which this result belongs. Only
     * one relationship to any test can exist at any given moment, so by setting
     * a test any existing relationship will be removed.
     *
     * @param test the {@linkplain TestNode test} to set
     * @return the same {@linkplain TestResultNode test result} instance to
     * support fluent API operations
     */
    public TestResultNode setTest(TestNode test) {
        getSingleOutgoingRelationship(RelationshipTypes.IS_RESULT_OF_TEST).ifPresent(rel -> rel.delete());
        createUniqueRelationshipTo(test, RelationshipTypes.IS_RESULT_OF_TEST);
        return this;
    }

    @Override
    public TestNode getTest() {
        return getOptionalTest().orElse(null);
    }

    /**
     * Returns the test result's test. This is essentially the same operation as
     * {@linkplain #getTest()} with the difference of using Java 8's Optional to
     * avoid returning null.
     *
     * @return the test as an {@linkplain Optional}
     */
    public Optional<TestNode> getOptionalTest() {
        return getSingleOutgoingRelationship(RelationshipTypes.IS_RESULT_OF_TEST).map(
            rel -> TestNodeFactory.wrap(rel.getEndNode()));
    }

    /**
     * Returns the {@linkplain TestGroupResultNode test group result} this
     * {@linkplain TestResultNode test result} belongs to as an
     * {@linkplain Optional optional}.
     *
     * @return the test group result
     */
    public Optional<TestGroupResultNode> getTestGroupResult() {
        return getSingleIncomingRelationship(RelationshipTypes.CONTAINS).map(
            rel -> TestGroupResultNodeFactory.wrap(rel.getStartNode()));
    }

}
