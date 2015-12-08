package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class MetadataKindDataTest {

    @Test
    public void testCopyConstructor() {

        MetadataKindData original = TestUtils.defaultMetadataKind();
        MetadataKindData copy = new MetadataKindData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

}
