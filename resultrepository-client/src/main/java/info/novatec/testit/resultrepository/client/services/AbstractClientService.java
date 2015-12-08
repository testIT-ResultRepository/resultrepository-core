package info.novatec.testit.resultrepository.client.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public abstract class AbstractClientService {

    private String baseUrl;
    private RestTemplate template;

    public AbstractClientService(String baseUrl) {

        this.baseUrl = baseUrl;
        this.template = new RestTemplate();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<HttpMessageConverter<?>> converters = new LinkedList<HttpMessageConverter<?>>();
        converters.add(converter);
        this.template.setMessageConverters(converters);

    }

    protected String buildUrl(String context) {
        return getBaseUrl() + context;
    }

    protected String buildUrl(String context, Long id) {
        return getBaseUrl() + StringUtils.replace(context, "{id}", String.valueOf(id));
    }

    protected <T> Set<T> asSet(T[] data) {
        return new HashSet<T>(Arrays.asList(data));
    }

    protected <T> List<T> asList(T[] data) {
        return new LinkedList<T>(Arrays.asList(data));
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected RestTemplate getTemplate() {
        return template;
    }

}
