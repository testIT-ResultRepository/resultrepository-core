package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class BuildJobDataTest {

    @Test
    public void testCopyConstructor() {

        BuildJobData original = TestUtils.defaultBuildJob();
        BuildJobData copy = new BuildJobData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

}
