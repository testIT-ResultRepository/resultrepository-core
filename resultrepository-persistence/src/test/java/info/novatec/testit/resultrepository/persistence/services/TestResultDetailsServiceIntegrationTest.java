package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.TestResultDetailsService;


public class TestResultDetailsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    TestResultDetailsService cut;

    @Test
    public void testFindTestResultDetailById() {

        TestResultDetailData detail1 = createTestResultDetail();
        TestResultDetailData detail2 = createTestResultDetail();

        TestResultDetailData foundDetail1 = cut.findById(detail1.getId());
        TestResultDetailData foundDetail2 = cut.findById(detail2.getId());

        assertThat(foundDetail1).isEqualTo(detail1);
        assertThat(foundDetail2).isEqualTo(detail2);

    }

    @Test
    public void testFindTestResultForTestResultDetailWithId() {

        TestResultData testResult = createTestResult();
        TestResultDetailData detail1 = createTestResultDetail();
        TestResultDetailData detail2 = createTestResultDetail(testResult);

        TestResultData foundTestResult1 = cut.findTestResult(detail1.getId());
        TestResultData foundTestResult2 = cut.findTestResult(detail2.getId());

        assertThat(foundTestResult1).isNull();
        assertThat(foundTestResult2.getId()).isEqualTo(testResult.getId());

    }

}
