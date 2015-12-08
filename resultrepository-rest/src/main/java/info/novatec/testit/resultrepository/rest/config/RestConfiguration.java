package info.novatec.testit.resultrepository.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Configuration
public class RestConfiguration {

    @Configuration
    @ComponentScan("info.novatec.testit.resultrepository.rest.controller")
    public static class ControllerConfiguration {
        // controller are automatically loaded using component scan
    }

    @Bean
    public ObjectMapper objectMapper() {

        /* Overrides the default {@link ObjectMapper} instance in order to allow
         * for configuration of the mapping process. */

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objMapper;

    }

}
