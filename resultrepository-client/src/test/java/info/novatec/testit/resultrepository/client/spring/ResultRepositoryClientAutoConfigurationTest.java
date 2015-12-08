package info.novatec.testit.resultrepository.client.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.client.services.BuildJobsClientService;
import info.novatec.testit.resultrepository.client.services.BuildsClientService;
import info.novatec.testit.resultrepository.client.services.ImportClientService;
import info.novatec.testit.resultrepository.client.services.TagsClientService;
import info.novatec.testit.resultrepository.client.services.TestGroupResultsClientService;
import info.novatec.testit.resultrepository.client.services.TestGroupsClientService;
import info.novatec.testit.resultrepository.client.services.TestResultDetailsClientService;
import info.novatec.testit.resultrepository.client.services.TestResultsClientService;
import info.novatec.testit.resultrepository.client.services.TestsClientService;
import info.novatec.testit.resultrepository.client.spring.ResultRepositoryClientAutoConfigurationTest.ResultRepositoryClientAutoConfigurationTestConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResultRepositoryClientAutoConfigurationTestConfiguration.class)
public class ResultRepositoryClientAutoConfigurationTest {

    @Configuration
    @EnableAutoConfiguration
    public interface ResultRepositoryClientAutoConfigurationTestConfiguration {
        // no content
    }

    @Autowired
    BuildJobsClientService buildJobsClientService;
    @Autowired
    BuildsClientService buildsClientService;
    @Autowired
    ImportClientService importClientService;
    @Autowired
    TagsClientService tagsClientService;
    @Autowired
    TestGroupResultsClientService testGroupResultsClientService;
    @Autowired
    TestGroupsClientService testGroupsClientService;
    @Autowired
    TestResultDetailsClientService testResultDetailsClientService;
    @Autowired
    TestResultsClientService testResultsClientService;
    @Autowired
    TestsClientService testsClientService;

    @Test
    public void testThatAllServicesAreAutomaticallyInitializedAsBeans() {
        assertThat(buildJobsClientService).isNotNull();
        assertThat(buildsClientService).isNotNull();
        assertThat(importClientService).isNotNull();
        assertThat(tagsClientService).isNotNull();
        assertThat(testGroupResultsClientService).isNotNull();
        assertThat(testGroupsClientService).isNotNull();
        assertThat(testResultDetailsClientService).isNotNull();
        assertThat(testResultsClientService).isNotNull();
        assertThat(testsClientService).isNotNull();
    }

}
