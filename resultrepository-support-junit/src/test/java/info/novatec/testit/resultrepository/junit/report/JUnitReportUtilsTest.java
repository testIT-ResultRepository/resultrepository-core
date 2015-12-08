package info.novatec.testit.resultrepository.junit.report;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class JUnitReportUtilsTest {

    @Test
    public void testConvertReportTimeToMillis_0_0Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("0");
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testConvertReportTimeToMillis_1_1000Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("1");
        assertThat(result).isEqualTo(1000L);
    }

    @Test
    public void testConvertReportTimeToMillis_0Point0_0Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("0.0");
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testConvertReportTimeToMillis_0Point000_0Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("0.000");
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testConvertReportTimeToMillis_0Point123_123Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("0.123");
        assertThat(result).isEqualTo(123L);
    }

    @Test
    public void testConvertReportTimeToMillis_1Point123_1123Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("1.123");
        assertThat(result).isEqualTo(1123L);
    }

    @Test
    public void testConvertReportTimeToMillis_500Point123_1123Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("500.123");
        assertThat(result).isEqualTo(500123L);
    }

    @Test
    public void testConvertReportTimeToMillis_NullString_0Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis(null);
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testConvertReportTimeToMillis_EmptyString_0Millis() {
        long result = JUnitReportUtils.convertReportTimeToMillis("");
        assertThat(result).isEqualTo(0L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertReportTimeToMillis_TwoPoints_Exception() {
        JUnitReportUtils.convertReportTimeToMillis("1.1.1");
    }

}
