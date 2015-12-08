package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class MetadataKindNodeFactoryIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String METADATA_KIND = "browser";
    private static final String ANOTHER_METADATA_KIND = "os";
    private static final String UNKNOWN_METADATA_KIND = "unknown";

    private static final long UNKNOWN_ID = -1L;

    /* get or create */

    @Test
    public void testGetOrCreateFromGraph_CreatesCorrectNodesOnFirstExecution() {
        inTransaction(() -> {

            MetadataKindNode metadataKind = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);

            assertThat(metadataKind.getName()).isEqualTo(METADATA_KIND);
            assertThat(metadataKind.getDescription()).isNull();

        });
    }

    @Test
    public void testGetOrCreateFromGraph_SecondExecutionReturnsSameNodeInNewWrapper() {
        inTransaction(() -> {

            MetadataKindNode metadataKind1 = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);
            MetadataKindNode metadataKind2 = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);

            assertThat(metadataKind2).isEqualTo(metadataKind1);

        });
    }

    @Test
    public void testGetOrCreateFromGraph_DifferenDataReturnDifferentNodes() {
        inTransaction(() -> {

            MetadataKindNode metadataKind1 = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);
            MetadataKindNode metadataKind2 = metadataKindFactory.getOrCreateFromGraph(ANOTHER_METADATA_KIND);

            assertThat(metadataKind2).isNotEqualTo(metadataKind1);

        });
    }

    /* lookup for name */

    @Test
    public void testThatLookingUpNonExistingMetadataKindWithNameReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<MetadataKindNode> metadataKind = metadataKindFactory.getFromGraph(UNKNOWN_METADATA_KIND);
            assertThat(metadataKind.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingMetadataKindWithNameReturnsItAsAnOptional() {
        inTransaction(() -> {

            MetadataKindNode metadataKind1 = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);
            MetadataKindNode metadataKind2 = metadataKindFactory.getFromGraph(METADATA_KIND).get();

            assertThat(metadataKind2).isEqualTo(metadataKind1);

        });
    }

    /* lookup for id */

    @Test
    public void testThatLookingUpNonExistingMetadataKindWithIdReturnsEmptyOptional() {
        inTransaction(() -> {
            Optional<MetadataKindNode> metadataKind = metadataKindFactory.getFromGraph(UNKNOWN_ID);
            assertThat(metadataKind.isPresent()).isFalse();
        });
    }

    @Test
    public void testThatLookingUpExistingMetadataKindWithIdReturnsItAsAnOptional() {
        inTransaction(() -> {

            MetadataKindNode metadataKind1 = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);
            MetadataKindNode metadataKind2 = metadataKindFactory.getFromGraph(metadataKind1.getId()).get();

            assertThat(metadataKind2).isEqualTo(metadataKind1);

        });
    }

    /* get all */

    @Test
    public void testThatGettingAllMetadataKindsFromEmptyGraphReturnsEmptyStream() {
        inTransaction(() -> {
            assertEmpty(metadataKindFactory.getAllFromGraph());
        });
    }

    @Test
    public void testThatGettingAllMetadataKindsFromGraphReturnsThemAllAsAStream() {
        inTransaction(() -> {

            MetadataKindNode metadataKind1 = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);
            MetadataKindNode metadataKind2 = metadataKindFactory.getOrCreateFromGraph(ANOTHER_METADATA_KIND);

            Stream<MetadataKindNode> metadataKinds = metadataKindFactory.getAllFromGraph();

            assertThat(metadataKinds.collect(Collectors.toSet())).containsOnly(metadataKind1, metadataKind2);

        });
    }

}
