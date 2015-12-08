package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import info.novatec.testit.resultrepository.api.interfaces.BuildJob;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


/**
 * This class wraps a {@linkplain Labels#BUILD_JOB} labeled node and provides
 * different operations for interacting with it as a build job.
 */
public class BuildJobNode extends AbstractNodeWrapper implements BuildJob {

    /* ### Graph node properties START
     * 
     * Changing the value of any of these will break compatibility with older
     * version! If you have to change one of the values you have to write some
     * kind of migration code as well! */

    public static final String PROPERTY_NAME = "name";

    /* ### Graph node properties END */

    /**
     * Creates a new {@linkplain BuildJobNode build job} using the given name.
     * Calling this constructor will create a new {@linkplain Node node} in the
     * graph. If you want to wrap an existing {@linkplain Node node} as a
     * {@linkplain BuildJobNode build job} use {@linkplain #BuildJobNode(Node)}
     * instead.
     *
     * @param graphDb the {@linkplain GraphDatabaseService graph} to use when
     * creating the {@linkplain Node node}
     * @param name the name of this {@linkplain BuildJobNode build job}
     */
    protected BuildJobNode(GraphDatabaseService graphDb, String name) {
        super(graphDb, Labels.BUILD_JOB);
        setRequiredProperty(PROPERTY_NAME, name);
    }

    /**
     * Creates a new {@linkplain BuildJobNode build job} by wrapping the given
     * {@linkplain Node node}. Validates if the given node has the required
     * label {@linkplain Labels#BUILD_JOB}.
     *
     * @param node the node to wrap as a {@linkplain BuildJobNode build job}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#BUILD_JOB} node
     */
    protected BuildJobNode(Node node) {
        super(node, Labels.BUILD_JOB);
    }

    @Override
    public String getName() {
        return getStringProperty(PROPERTY_NAME).get();
    }

    /**
     * Returns all {@linkplain BuildNode builds} of this
     * {@linkplain BuildJobNode build job} as an ordered list. The order of the
     * list is defined by the order in which the {@linkplain BuildNode builds}
     * were originally added to the {@linkplain BuildJobNode build job}.
     *
     * @return the ordered list
     */
    public List<BuildNode> getBuildsInOrder() {

        List<BuildNode> returnValue = new LinkedList<>();

        Optional<BuildNode> build = getFirstBuild();
        while (build.isPresent()) {
            returnValue.add(build.get());
            build = build.get().getNextBuild();
        }

        return returnValue;

    }

    /**
     * Returns the latest x {@linkplain BuildNode builds} of this
     * {@linkplain BuildJobNode build job} as an ordered list. If the maximum is
     * higher than the number of builds, all builds will be returned. The order
     * of the list is defined by the order in which the {@linkplain BuildNode
     * builds} were originally added to the {@linkplain BuildJobNode build job}.
     * <p>
     * <b>Example:</b>
     * </p>
     * <p>
     * A build job with 5 builds (#1, #2, #3, #4, #5) will return a list
     * containing [#3, #4, #5] if <code>maxNumberOfBuilds</code> is set to 3.
     * </p>
     *
     * @param maxNumberOfBuilds the maximum amount of builds to return in the
     * list
     * @return the limited and ordered list
     */
    public List<BuildNode> getLatestBuilds(int maxNumberOfBuilds) {

        LinkedList<BuildNode> returnValue = new LinkedList<>();

        Optional<BuildNode> build = getLastBuild();
        while (build.isPresent() && (returnValue.size() < maxNumberOfBuilds)) {
            returnValue.addFirst(build.get());
            build = build.get().getPreviousBuild();
        }

        return returnValue;

    }

    /**
     * Returns the first (oldest) recorded {@linkplain BuildNode build} of this
     * {@linkplain BuildJobNode build job} as an {@linkplain Optional}.
     *
     * @return the first build
     */
    public Optional<BuildNode> getFirstBuild() {
        return getSingleOutgoingRelationship(RelationshipTypes.FIRST_BUILD).map(
            rel -> BuildNodeFactory.wrap(rel.getEndNode()));
    }

    /**
     * Returns the last (newest) recorded {@linkplain BuildNode build} of this
     * {@linkplain BuildJobNode build job} as an {@linkplain Optional}.
     *
     * @return the last build
     */
    public Optional<BuildNode> getLastBuild() {
        return getSingleOutgoingRelationship(RelationshipTypes.LAST_BUILD).map(
            rel -> BuildNodeFactory.wrap(rel.getEndNode()));
    }

    /**
     * Returns all of the {@linkplain BuildNode builds} this
     * {@linkplain BuildJobNode build job} is linked with as a {@linkplain Set
     * set}.
     * <p>
     * This method exists to make it more comfortable to get to all builds with
     * one method call. The preferred way to get the {@linkplain BuildNode
     * builds} of this {@linkplain BuildJobNode build job} is to use the
     * {@linkplain Stream stream} returning {@linkplain #getBuildsStream()}
     * method instead.
     *
     * @return the builds of this {@linkplain BuildJobNode build job} as a Set
     */
    public Set<BuildNode> getBuilds() {
        return getBuildsStream().collect(Collectors.toSet());
    }

    /**
     * Returns all of the {@linkplain BuildNode builds} this
     * {@linkplain BuildJobNode build job} is linked with as a
     * {@linkplain Stream stream}.
     *
     * @return the builds of this {@linkplain BuildJobNode build job} as a
     * {@linkplain Stream stream}
     */
    public Stream<BuildNode> getBuildsStream() {
        return getOutgoingRelationships(RelationshipTypes.CONTAINS).map(rel -> BuildNodeFactory.wrap(rel.getEndNode()));
    }

}
