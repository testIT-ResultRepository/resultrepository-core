package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class TagNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String TAG = "tag";
    private static final String ANOTHER_TAG = "another";
    private static final String UNKNOWN_TAG = "unknown";

    private static final long UNKNOWN_ID = -1L;

    /* get or create */

    @Test
    public void testGetOrCreateFromGraph_CreatesCorrectNodeOnFirstExecution() {
        inTransaction(() -> {
            TagNode tag = tagFactory.getOrCreateFromGraph(TAG);
            assertThat(tag.getValue()).isEqualTo(TAG);
        });
    }

    @Test
    public void testGetOrCreateFromGraph_SecondExecutionReturnsSameNodeInNewWrapper() {
        inTransaction(() -> {

            TagNode tag1 = tagFactory.getOrCreateFromGraph(TAG);
            TagNode tag2 = tagFactory.getOrCreateFromGraph(TAG);

            assertThat(tag2).isEqualTo(tag1);

        });
    }

    @Test
    public void testGetOrCreateFromGraph_DifferenDataReturnDifferentNodes() {
        inTransaction(() -> {

            TagNode tag1 = tagFactory.getOrCreateFromGraph(TAG);
            TagNode tag2 = tagFactory.getOrCreateFromGraph(ANOTHER_TAG);

            assertThat(tag2).isNotEqualTo(tag1);

        });
    }

    /* lookup for name */

    @Test
    public void testThatLookingUpNonExistingTagWithNameReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TagNode> tag = tagFactory.getFromGraph(UNKNOWN_TAG);
            assertThat(tag.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTagWithNameReturnsItAsAnOptional() {
        inTransaction(() -> {

            TagNode tag1 = tagFactory.getOrCreateFromGraph(TAG);
            TagNode tag2 = tagFactory.getFromGraph(TAG).get();

            assertThat(tag2).isEqualTo(tag1);

        });
    }

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingTagWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<TagNode> tag = tagFactory.getFromGraph(UNKNOWN_ID);
            assertThat(tag.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingTagWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            TagNode tag1 = tagFactory.getOrCreateFromGraph(TAG);
            TagNode tag2 = tagFactory.getFromGraph(tag1.getId()).get();

            assertThat(tag2).isEqualTo(tag1);

        });
    }

    /* get all */

    @Test
    public void testThatGettingAllTagsFromEmptyGraphReturnsEmptyStream() {
        inTransaction(() -> {
            assertEmpty(tagFactory.getAllFromGraph());
        });
    }

    @Test
    public void testThatGettingAllTagsFromGraphReturnsThemAllAsAStream() {
        inTransaction(() -> {

            TagNode tag1 = tagFactory.getOrCreateFromGraph(TAG);
            TagNode tag2 = tagFactory.getOrCreateFromGraph(ANOTHER_TAG);

            Stream<TagNode> tags = tagFactory.getAllFromGraph();

            assertThat(tags.collect(Collectors.toSet())).containsOnly(tag1, tag2);

        });
    }

}
