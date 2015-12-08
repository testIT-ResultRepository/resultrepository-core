package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class MetadataKindNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final String METADATA_KIND = "browser";
    private static final String ANOTHER_METADATA_KIND = "os";

    private static final String DESCRIPTION = "foo bar";

    @Test(expected = IllegalArgumentException.class)
    public void testThatNameIsARequiredProperty() {
        inTransaction(() -> {
            new MetadataKindNode(testGraph, null);
        });
    }

    /* properties */

    @Test
    public void testThatDescriptionCanBeSetAndGet() {
        inTransaction(() -> {

            MetadataKindNode metadataKind = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);

            metadataKind.setDescription(DESCRIPTION);
            assertThat(metadataKind.getDescription()).isEqualTo(DESCRIPTION);

        });
    }

    @Test
    public void testThatDescriptionIsNotARequiredAttribute() {
        inTransaction(() -> {

            MetadataKindNode metadataKind = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);

            metadataKind.setDescription(null);
            assertThat(metadataKind.getDescription()).isNull();

        });
    }

    /* meta data values */

    @Test
    public void testThatDirectMetadataValuesCanBeRetrieved() {

        inTransaction(() -> {

            MetadataPath path1 = new MetadataPath(METADATA_KIND, "firefox");
            MetadataPath path2 = new MetadataPath(METADATA_KIND, "internet explorer");
            MetadataPath path3 = new MetadataPath(METADATA_KIND, "internet explorer", "11");
            MetadataPath path4 = new MetadataPath(ANOTHER_METADATA_KIND, "windows", "7");

            MetadataKindNode metadataKind = metadataKindFactory.getOrCreateFromGraph(METADATA_KIND);

            MetadataValueNode metadataValue1 = metadataValueFactory.getOrCreateFromGraph(path1);
            MetadataValueNode metadataValue2 = metadataValueFactory.getOrCreateFromGraph(path2);
            MetadataValueNode metadataValue3 = metadataValueFactory.getOrCreateFromGraph(path3);
            MetadataValueNode metadataValue4 = metadataValueFactory.getOrCreateFromGraph(path4);

            Set<MetadataValueNode> metadataValues = metadataKind.getDirectMetadataValues();
            assertThat(metadataValues).containsOnly(metadataValue1, metadataValue2)
                .doesNotContain(metadataValue3, metadataValue4);

        });
    }

}
