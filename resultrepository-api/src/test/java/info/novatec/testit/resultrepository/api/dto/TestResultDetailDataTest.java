package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class TestResultDetailDataTest {

    @Test
    public void testCopyConstructor() {

        TestResultDetailData original = TestUtils.defaultTestResultDetail();
        TestResultDetailData copy = new TestResultDetailData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

}
