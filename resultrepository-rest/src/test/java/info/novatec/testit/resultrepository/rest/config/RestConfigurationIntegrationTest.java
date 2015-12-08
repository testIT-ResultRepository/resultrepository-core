package info.novatec.testit.resultrepository.rest.config;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.metrics.api.services.MetricsReporter;
import info.novatec.testit.resultrepository.rest.config.RestConfigurationIntegrationTest.RestConfigurationIntegrationTestConfiguration;
import info.novatec.testit.resultrepository.server.api.AsynchronousImportService;
import info.novatec.testit.resultrepository.server.api.BuildJobsService;
import info.novatec.testit.resultrepository.server.api.BuildsService;
import info.novatec.testit.resultrepository.server.api.ImportService;
import info.novatec.testit.resultrepository.server.api.StatisticsService;
import info.novatec.testit.resultrepository.server.api.TagsService;
import info.novatec.testit.resultrepository.server.api.TestGroupResultsService;
import info.novatec.testit.resultrepository.server.api.TestGroupsService;
import info.novatec.testit.resultrepository.server.api.TestResultDetailsService;
import info.novatec.testit.resultrepository.server.api.TestResultsService;
import info.novatec.testit.resultrepository.server.api.TestsService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { RestConfiguration.class, RestConfigurationIntegrationTestConfiguration.class })
public class RestConfigurationIntegrationTest {

    @Configuration
    public static class RestConfigurationIntegrationTestConfiguration {

        /* server services */

        @Bean
        public AsynchronousImportService asynchronousImportService() {
            return mock(AsynchronousImportService.class);
        }

        @Bean
        public BuildJobsService buildJobsService() {
            return mock(BuildJobsService.class);
        }

        @Bean
        public BuildsService buildsService() {
            return mock(BuildsService.class);
        }

        @Bean
        public ImportService importService() {
            return mock(ImportService.class);
        }

        @Bean
        public StatisticsService statisticsService() {
            return mock(StatisticsService.class);
        }

        @Bean
        public TagsService tagsService() {
            return mock(TagsService.class);
        }

        @Bean
        public TestGroupResultsService testGroupResultsService() {
            return mock(TestGroupResultsService.class);
        }

        @Bean
        public TestGroupsService testGroupsService() {
            return mock(TestGroupsService.class);
        }

        @Bean
        public TestResultDetailsService testResultDetailsService() {
            return mock(TestResultDetailsService.class);
        }

        @Bean
        public TestResultsService testResultsService() {
            return mock(TestResultsService.class);
        }

        @Bean
        public TestsService testsService() {
            return mock(TestsService.class);
        }

        /* metrics */

        @Bean
        public MetricsReporter metricsReporter() {
            return mock(MetricsReporter.class);
        }

    }

    @Test
    public void testThatContextIsLoaded() {
        // dummy test in order to trigger context load
    }

}
