package info.novatec.testit.resultrepository.persistence.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.persistence.AbstractPersistenceIntegrationTest;
import info.novatec.testit.resultrepository.server.api.TestGroupsService;


public class TestGroupsServiceIntegrationTest extends AbstractPersistenceIntegrationTest {

    @Autowired
    TestGroupsService cut;

    @Test
    public void testPersistenceOfNewTestGroup() {

        TestGroupData testGroup = new TestGroupData().setName("testGroup");

        TestGroupData persistedTestGroup = cut.persist(testGroup);

        assertThat(persistedTestGroup.getId()).isGreaterThanOrEqualTo(0);
        assertThat(persistedTestGroup.getName()).isEqualTo("testGroup");

    }

    @Test
    public void testThatMultiplePersistenceOfSameTestGroupWillOnlyCreateOneTestGroupInRepository() {

        TestGroupData testGroup = new TestGroupData().setName("testGroup");

        TestGroupData persistedTestGroup1 = cut.persist(testGroup);
        TestGroupData persistedTestGroup2 = cut.persist(testGroup);

        assertThat(persistedTestGroup1).isEqualTo(persistedTestGroup2);
        assertThat(persistedTestGroup1).isNotSameAs(persistedTestGroup2);

    }

    @Test
    public void testFindAllTestGroups() {

        TestGroupData testGroup1 = createTestGroup("testGroup1");
        TestGroupData testGroup2 = createTestGroup("testGroup2");

        Set<TestGroupData> testGroups = cut.findAll();

        assertThat(testGroups).containsOnly(testGroup1, testGroup2);

    }

    @Test
    public void testFindAllTestsWithFilter() {

        TestGroupData testGroup1 = createTestGroup("fooTestGroup");
        TestGroupData testGroup2 = createTestGroup("barTestGroup");
        TestGroupData testGroup3 = createTestGroup("fooBarTestGroup");

        Set<TestGroupData> testGroups = cut.findAll(testGroup -> testGroup.getName().startsWith("foo"));

        assertThat(testGroups).containsOnly(testGroup1, testGroup3).doesNotContain(testGroup2);

    }

    @Test
    public void testFindTestGroupById() {

        TestGroupData testGroup1 = createTestGroup("testGroup1");
        TestGroupData testGroup2 = createTestGroup("testGroup2");

        TestGroupData foundTestGroup1 = cut.findById(testGroup1.getId());
        TestGroupData foundTestGroup2 = cut.findById(testGroup2.getId());

        assertThat(foundTestGroup1).isEqualTo(testGroup1);
        assertThat(foundTestGroup2).isEqualTo(testGroup2);

    }

    @Test
    public void testFindTestGroupResultsForTestGroupWithId() {

        TestGroupData testGroup = createTestGroup("testGroup");
        TestGroupResultData result1 = createTestGroupResult(testGroup);
        TestGroupResultData result2 = createTestGroupResult(testGroup);
        TestGroupResultData result3 = createTestGroupResult(testGroup);

        Set<TestGroupResultData> foundResults = cut.findTestGroupResults(testGroup.getId());

        assertThat(foundResults).containsOnly(result1, result2, result3);

    }

    @Test
    public void testFindTestGroupResultsForTestGroupWithIdWithFilter() {

        TestGroupData testGroup = createTestGroup("testGroup");
        TestGroupResultData result1 = createTestGroupResult(testGroup);
        TestGroupResultData result2 = createTestGroupResult(testGroup);
        TestGroupResultData result3 = createTestGroupResult(testGroup);

        Set<Long> ids = Sets.newSet(result1.getId(), result3.getId());

        Predicate<TestGroupResult> filter = result -> ids.contains(result.getId());
        Set<TestGroupResultData> foundResults = cut.findTestGroupResults(testGroup.getId(), filter);

        assertThat(foundResults).containsOnly(result1, result3).doesNotContain(result2);

    }

}
