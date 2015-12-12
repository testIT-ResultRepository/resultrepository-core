package info.novatec.testit.resultrepository.plugin.junit.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import info.novatec.testit.resultrepository.junit.JUnitReportConverter;


@Configuration
public class JUnitPluginAutoConfiguration {

    @Configuration
    @ComponentScan("info.novatec.testit.resultrepository.plugin.junit.controller")
    public static class ControllerConfiguration {
        // controller are automatically loaded using component scan
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        /* Provide a default ObjectMapper instance in case there isn't already one. */
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objMapper;

    }

    @Bean
    public JUnitReportConverter jUnitReportConverter() {
        return new JUnitReportConverter();
    }

}
