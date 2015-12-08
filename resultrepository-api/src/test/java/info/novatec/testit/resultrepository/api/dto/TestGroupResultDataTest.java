package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class TestGroupResultDataTest {

    @Test
    public void testCopyConstructor() {

        TestGroupResultData original = TestUtils.defaultTestGroupResult();
        TestGroupResultData copy = new TestGroupResultData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

    @Test
    public void testCopyConstructor_NoTestGroupData_NullSet() {

        TestGroupResultData original = TestUtils.defaultTestGroupResult();
        original.setTestGroup(null);

        TestGroupResultData copy = new TestGroupResultData(original);
        assertThat(copy.getTestGroup()).isNull();

    }

    @Test
    public void testCopyConstructor_NoBuildData_NullSet() {

        TestGroupResultData original = TestUtils.defaultTestGroupResult();
        original.setBuild(null);

        TestGroupResultData copy = new TestGroupResultData(original);
        assertThat(copy.getBuild()).isNull();

    }

}
