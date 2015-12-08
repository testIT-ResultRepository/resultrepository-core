package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class BuildDataTest {

    @Test
    public void testCopyConstructor() {

        BuildData original = TestUtils.defaultBuild();
        BuildData copy = new BuildData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

}
