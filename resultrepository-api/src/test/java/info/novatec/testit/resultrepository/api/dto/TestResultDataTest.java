package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class TestResultDataTest {

    @Test
    public void testCopyConstructor() {

        TestResultData original = TestUtils.defaultTestResult();
        TestResultData copy = new TestResultData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

    @Test
    public void testCopyConstructor_NoTestData_NullSet() {

        TestResultData original = TestUtils.defaultTestResult();
        original.setTest(null);

        TestResultData copy = new TestResultData(original);
        assertThat(copy.getTest()).isNull();

    }

}
