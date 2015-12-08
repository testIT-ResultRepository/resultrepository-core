package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class TestGroupDataTest {

    @Test
    public void testCopyConstructor() {

        TestGroupData original = TestUtils.defaultTestGroup();
        TestGroupData copy = new TestGroupData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

}
