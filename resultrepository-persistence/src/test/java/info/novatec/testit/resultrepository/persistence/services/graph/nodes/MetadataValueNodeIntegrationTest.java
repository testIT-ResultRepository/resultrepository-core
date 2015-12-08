package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;


public class MetadataValueNodeIntegrationTest extends AbstractPersistenceIntegrationTest {

    private static final MetadataPath FIREFOX = new MetadataPath("browser", "firefox");
    private static final MetadataPath FIREFOX_22 = new MetadataPath("browser", "firefox", "22");

    private static final MetadataPath WINDOWS = new MetadataPath("os", "windows");
    private static final MetadataPath WINDOWS_XP = new MetadataPath("os", "windows", "xp");
    private static final MetadataPath WINDOWS_XP_SP1 = new MetadataPath("os", "windows", "xp", "sp1");
    private static final MetadataPath WINDOWS_7 = new MetadataPath("os", "windows", "7");
    private static final MetadataPath WINDOWS_7_SP1 = new MetadataPath("os", "windows", "7", "sp1");

    /* multi-level meta values */

    @Test
    public void testLevel1MetadataValue() {
        inTransaction(() -> {

            MetadataValueNode metadataValue = metadataValueFactory.getOrCreateFromGraph(FIREFOX);
            MetadataKindNode metadataKind = metadataKindFactory.getFromGraph("browser").get();

            assertThat(metadataValue.getMetadataKind()).isEqualTo(metadataKind);
            assertThat(metadataValue.getValue()).isEqualTo("firefox");
            assertThat(metadataValue.getGeneralization().isPresent()).isFalse();

        });
    }

    @Test
    public void testLevel2MetadataValue() {
        inTransaction(() -> {

            MetadataValueNode metadataValue1 = metadataValueFactory.getOrCreateFromGraph(FIREFOX_22);
            MetadataValueNode metadataValue2 = metadataValueFactory.getOrCreateFromGraph(FIREFOX);
            MetadataKindNode metadataKind = metadataKindFactory.getFromGraph("browser").get();

            assertThat(metadataValue1.getMetadataKind()).isEqualTo(metadataKind);
            assertThat(metadataValue1.getValue()).isEqualTo("firefox:22");
            assertThat(metadataValue1.getGeneralization().get()).isEqualTo(metadataValue2);

        });
    }

    /* specializations */

    @Test
    public void testGetSpecializations_TwoSpecializations_SetWithExactlyTwoEntries() {
        inTransaction(() -> {

            MetadataValueNode metadataValue1 = metadataValueFactory.getOrCreateFromGraph(WINDOWS);
            MetadataValueNode metadataValue2 = metadataValueFactory.getOrCreateFromGraph(WINDOWS_XP);
            MetadataValueNode metadataValue3 = metadataValueFactory.getOrCreateFromGraph(WINDOWS_7);
            MetadataValueNode metadataValue4 = metadataValueFactory.getOrCreateFromGraph(WINDOWS_7_SP1);

            Set<MetadataValueNode> specializations = metadataValue1.getSpecializations();
            assertThat(specializations).containsOnly(metadataValue2, metadataValue3).doesNotContain(metadataValue4);

        });
    }

    /* paths */

    @Test
    public void testGetPath_MultipleValuesOnDifferenLevels_PathsAreCorrectlyReconstructed() {
        inTransaction(() -> {

            MetadataValueNode metadataValue1 = metadataValueFactory.getOrCreateFromGraph(WINDOWS);
            MetadataValueNode metadataValue2 = metadataValueFactory.getOrCreateFromGraph(WINDOWS_XP);
            MetadataValueNode metadataValue3 = metadataValueFactory.getOrCreateFromGraph(WINDOWS_XP_SP1);

            assertThat(metadataValue1.getPath()).isEqualTo(WINDOWS);
            assertThat(metadataValue2.getPath()).isEqualTo(WINDOWS_XP);
            assertThat(metadataValue3.getPath()).isEqualTo(WINDOWS_XP_SP1);

        });
    }

}
