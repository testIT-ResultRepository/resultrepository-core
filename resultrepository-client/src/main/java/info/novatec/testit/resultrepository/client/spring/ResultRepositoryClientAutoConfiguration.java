package info.novatec.testit.resultrepository.client.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.novatec.testit.resultrepository.client.services.BuildJobsClientService;
import info.novatec.testit.resultrepository.client.services.BuildsClientService;
import info.novatec.testit.resultrepository.client.services.ImportClientService;
import info.novatec.testit.resultrepository.client.services.TagsClientService;
import info.novatec.testit.resultrepository.client.services.TestGroupResultsClientService;
import info.novatec.testit.resultrepository.client.services.TestGroupsClientService;
import info.novatec.testit.resultrepository.client.services.TestResultDetailsClientService;
import info.novatec.testit.resultrepository.client.services.TestResultsClientService;
import info.novatec.testit.resultrepository.client.services.TestsClientService;
import info.novatec.testit.resultrepository.client.spring.settings.ResultRepositorySettings;


@Configuration
public class ResultRepositoryClientAutoConfiguration {

    @Configuration
    @EnableConfigurationProperties(ResultRepositorySettings.class)
    public static class ServiceConfiguration {

        @Autowired
        private ResultRepositorySettings settings;

        @Bean
        @ConditionalOnMissingBean(BuildJobsClientService.class)
        public BuildJobsClientService buildJobsClientService() {
            return new BuildJobsClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(BuildsClientService.class)
        public BuildsClientService buildsClientService() {
            return new BuildsClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(ImportClientService.class)
        public ImportClientService importClientService() {
            return new ImportClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(TagsClientService.class)
        public TagsClientService tagsClientService() {
            return new TagsClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(TestGroupResultsClientService.class)
        public TestGroupResultsClientService testGroupResultsClientService() {
            return new TestGroupResultsClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(TestGroupsClientService.class)
        public TestGroupsClientService testGroupsClientService() {
            return new TestGroupsClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(TestResultDetailsClientService.class)
        public TestResultDetailsClientService testResultDetailsClientService() {
            return new TestResultDetailsClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(TestResultsClientService.class)
        public TestResultsClientService testResultsClientService() {
            return new TestResultsClientService(settings.getUrl());
        }

        @Bean
        @ConditionalOnMissingBean(TestsClientService.class)
        public TestsClientService testsClientService() {
            return new TestsClientService(settings.getUrl());
        }

    }

}
