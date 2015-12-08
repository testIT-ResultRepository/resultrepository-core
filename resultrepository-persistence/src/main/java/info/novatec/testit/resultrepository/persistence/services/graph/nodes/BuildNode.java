package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#BUILD} labeled node and provides
 * different operations for interacting with it as a build.
 */
public class BuildNode extends AbstractNodeWrapper implements Build {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_IDENTIFIER = "identifier";
    public static final String PROPERTY_NUMBER = "number";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain BuildNode build} using the given build job name
     * and build number. Calling this constructor will create a new
     * {@linkplain Node node} in the graph. If you want to wrap an existing
     * {@linkplain Node node} as a {@linkplain BuildNode build} use
     * {@linkplain #BuildNode(Node)} instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     * @param buildJobName the name of this this build's job
     * @param buildNumber the number of this build
     */
    protected BuildNode(GraphDatabaseService graphDb, String buildJobName, int buildNumber) {
        super(graphDb, Labels.BUILD);
        setRequiredProperty(PROPERTY_NUMBER, Integer.valueOf(buildNumber));
        setProperty(PROPERTY_IDENTIFIER, BuildNodeFactory.getIdentifier(buildJobName, buildNumber));
    }

    /**
     * Creates a new {@linkplain BuildNode build} by wrapping the given
     * {@linkplain Node node}. Validates if the given node has the required
     * label {@linkplain Labels#BUILD}.
     *
     * @param node the node to wrap as a {@linkplain BuildNode build}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#BUILD} node
     */
    protected BuildNode(Node node) {
        super(node, Labels.BUILD);
    }

    @Override
    public BuildJobNode getBuildJob() {
        return getOptionalBuildJob().orElse(null);
    }

    /**
     * Returns the build's job. This is essentially the same operation as
     * {@linkplain #getBuildJob()} with the difference of using Java 8's
     * Optional to avoid returning null.
     *
     * @return the build job as an {@linkplain Optional}
     */
    public Optional<BuildJobNode> getOptionalBuildJob() {
        return getSingleIncomingRelationship(RelationshipTypes.CONTAINS).map(
            rel -> BuildJobNodeFactory.wrap(rel.getStartNode()));
    }

    @Override
    public Integer getBuildNumber() {
        return getIntegerProperty(PROPERTY_NUMBER).get();
    }

    /**
     * Links this {@linkplain BuildNode build} with the given
     * {@linkplain TagNode tags}.
     *
     * @param tags the {@linkplain TagNode tags} to link to
     * @return the same {@linkplain BuildNode build} instance to support fluent
     * API operations
     */
    public BuildNode linkToTags(TagNode... tags) {
        return linkToTags(Arrays.asList(tags));
    }

    /**
     * Links this {@linkplain BuildNode build} with the given
     * {@linkplain TagNode tags}.
     *
     * @param tags the {@linkplain TagNode tags} to link to
     * @return the same {@linkplain BuildNode build} instance to support fluent
     * API operations
     */
    public BuildNode linkToTags(Collection<TagNode> tags) {
        for (TagNode tag : tags) {
            linkToTag(tag);
        }
        return this;
    }

    /**
     * Links this {@linkplain BuildNode build} with the given
     * {@linkplain TagNode tag}.
     *
     * @param tag the {@linkplain TagNode tag} to link to
     * @return the same {@linkplain BuildNode build} instance to support fluent
     * API operations
     */
    public BuildNode linkToTag(TagNode tag) {
        createUniqueRelationshipTo(tag, RelationshipTypes.IS_USED_BY_BUILD);
        return this;
    }

    /**
     * Returns all of the {@linkplain TagNode tags} this {@linkplain BuildNode
     * build} is linked with as a {@linkplain Set set}.
     * <p>
     * The preferred way to get the {@linkplain TagNode tags} of this
     * {@linkplain BuildNode build} is to use the {@linkplain Stream stream}
     * returning {@linkplain #getTagsStream()} method instead.
     *
     * @return the tags of this {@linkplain BuildNode build} as a Set
     */
    @Override
    public Set<TagNode> getTags() {
        return getTagsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all of the {@linkplain TagNode tags} this {@linkplain BuildNode
     * build} is linked with as a {@linkplain Stream stream}.
     *
     * @return the tags of this {@linkplain BuildNode build} as a
     * {@linkplain Stream stream}
     */
    public Stream<TagNode> getTagsStream() {
        return getRelationships(RelationshipTypes.IS_USED_BY_BUILD).map(
            rel -> TagNodeFactory.wrap(rel.getOtherNode(getNode())));
    }

    /**
     * Returns all {@linkplain TestGroupResultNode test group results} of this
     * {@linkplain BuildNode build} as a {@linkplain Set set}.
     * <p>
     * This is a comfort method for the usecases where all
     * {@linkplain TestGroupResultNode test group results} are needed. The
     * preferred way to retrieve the {@linkplain TestGroupResultNode test group
     * results} is to use the {@linkplain Stream stream} returning
     * {@linkplain #getTestGroupResultsStream()} method.
     *
     * @return the {@linkplain TestGroupResultNode test group results} as a
     * {@linkplain Set set}
     */
    public Set<TestGroupResultNode> getTestGroupResults() {
        return getTestGroupResultsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all {@linkplain TestGroupResultNode test group results} of this
     * {@linkplain BuildNode build} as a {@linkplain Stream stream}.
     *
     * @return the {@linkplain TestGroupResultNode test group results} as a
     * {@linkplain Stream stream}
     */
    public Stream<TestGroupResultNode> getTestGroupResultsStream() {
        return getIncomingRelationships(RelationshipTypes.IS_TEST_GROUP_RESULT_OF_BUILD).map(
            rel -> TestGroupResultNodeFactory.wrap(rel.getStartNode()));
    }

    /**
     * Returns the build before this one (chronologically). It is returned as an
     * {@linkplain Optional optional} because it may be that there is no
     * previous build.
     *
     * @return the previous build as an {@linkplain Optional optional}
     */
    public Optional<BuildNode> getPreviousBuild() {
        return getSingleIncomingRelationship(RelationshipTypes.NEXT_BUILD).map(
            rel -> BuildNodeFactory.wrap(rel.getStartNode()));
    }

    /**
     * Returns the build after this one (chronologically). It is returned as an
     * {@linkplain Optional optional} because it may be that there is no next
     * build.
     *
     * @return the next build as an {@linkplain Optional optional}
     */
    public Optional<BuildNode> getNextBuild() {
        return getSingleOutgoingRelationship(RelationshipTypes.NEXT_BUILD).map(
            rel -> BuildNodeFactory.wrap(rel.getEndNode()));
    }

}
