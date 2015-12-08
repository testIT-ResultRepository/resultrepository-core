package info.novatec.testit.resultrepository.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.TestUtils;


public class TestDataTest {

    @Test
    public void testCopyConstructor() {

        TestData original = TestUtils.defaultTest();
        TestData copy = new TestData(original);

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isEqualToComparingFieldByField(original);

    }

}
