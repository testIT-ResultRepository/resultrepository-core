package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.TestsService;


public class TestsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    TestsService cut;

    @Test
    public void testPersistenceOfNewTest() {

        TestData test = new TestData().setName("test");

        TestData persistedContent = cut.persist(test);

        assertThat(persistedContent.getId()).isGreaterThanOrEqualTo(0);
        assertThat(persistedContent.getName()).isEqualTo("test");

    }

    @Test
    public void testThatMultiplePersistenceOfSameTestWillOnlyCreateOneTestInRepository() {

        TestData test = new TestData().setName("test");

        TestData persistedContent1 = cut.persist(test);
        TestData persistedContent2 = cut.persist(test);

        assertThat(persistedContent1).isEqualTo(persistedContent2);
        assertThat(persistedContent1).isNotSameAs(persistedContent2);

    }

    @Test
    public void testFindAllTests() {

        TestData test1 = createTest("test1");
        TestData test2 = createTest("test2");

        Set<TestData> tests = cut.findAll();

        assertThat(tests).containsOnly(test1, test2);

    }

    @Test
    public void testFindAllTestsWithFilter() {

        TestData test1 = createTest("fooTest");
        TestData test2 = createTest("barTest");
        TestData test3 = createTest("fooBarTest");

        Set<TestData> tests = cut.findAll(test -> test.getName().startsWith("foo"));

        assertThat(tests).containsOnly(test1, test3).doesNotContain(test2);

    }

    @Test
    public void testFindTestById() {

        TestData test1 = createTest("test1");
        TestData test2 = createTest("test2");

        TestData foundTest1 = cut.findById(test1.getId());
        TestData foundTest2 = cut.findById(test2.getId());

        assertThat(foundTest1).isEqualTo(test1);
        assertThat(foundTest2).isEqualTo(test2);

    }

    @Test
    public void testFindTestResultsForTestWithId() {

        TestData test = createTest("test");
        TestResultData result1 = createTestResult(test);
        TestResultData result2 = createTestResult(test);
        TestResultData result3 = createTestResult(test);

        Set<TestResultData> foundResults = cut.findTestResults(test.getId());

        assertThat(foundResults).containsOnly(result1, result2, result3);

    }

    @Test
    public void testFindTestResultsForTestWithIdWithFilter() {

        TestData test = createTest("test");
        TestResultData result1 = createTestResult(test, ResultStatus.PASSED);
        TestResultData result2 = createTestResult(test, ResultStatus.FAILED);
        TestResultData result3 = createTestResult(test, ResultStatus.PASSED);

        Predicate<TestResult> filter = result -> result.getStatus().equals(ResultStatus.PASSED);
        Set<TestResultData> foundResults = cut.findTestResults(test.getId(), filter);

        assertThat(foundResults).containsOnly(result1, result3).doesNotContain(result2);

    }

}
