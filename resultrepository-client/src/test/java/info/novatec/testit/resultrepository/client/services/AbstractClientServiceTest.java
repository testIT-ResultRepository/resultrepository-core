package info.novatec.testit.resultrepository.client.services;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.lang.reflect.UndeclaredThrowableException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class AbstractClientServiceTest<T extends AbstractClientService> {

    static final String BASE_URL = "http://mock-server.com";
    static final long ID = 42L;
    static final long ANOTHER_ID = ID + 1;

    MockRestServiceServer mockServer;
    ObjectMapper mapper;

    T cut;

    @Before
    public void setUp() {
        cut = createClassUnderTest(BASE_URL);
        mockServer = MockRestServiceServer.createServer(cut.getTemplate());
        mapper = new ObjectMapper();
    }

    abstract T createClassUnderTest(String baseUrl);

    String withId(String contextPath, long id) {
        return StringUtils.replace(contextPath, "{id}", String.valueOf(id));
    }

    void setupPostForObjectExpectations(String contextPath, Object requestObject, Object expectedResponseObject) {
        mockServer.expect(requestTo(BASE_URL + contextPath))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().string(toJson(requestObject)))
            .andRespond(withSuccess(toJson(expectedResponseObject), MediaType.APPLICATION_JSON));
    }

    void setupPostForObjectWithoutResponseExpectations(String contextPath, Object requestObject) {
        mockServer.expect(requestTo(BASE_URL + contextPath))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().string(toJson(requestObject)))
            .andRespond(withSuccess());
    }

    void setupPostExceptionExpectations(String contextPath, Object requestObject) {
        mockServer.expect(requestTo(BASE_URL + contextPath))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().string(toJson(requestObject)))
            .andRespond(withServerError());
    }

    void setupGetForObjectExpectations(String contextPath, Object expectedResponseObject) {
        mockServer.expect(requestTo(BASE_URL + contextPath))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(toJson(expectedResponseObject), MediaType.APPLICATION_JSON));
    }

    void setupGetExceptionExpectations(String contextPath) {
        mockServer.expect(requestTo(BASE_URL + contextPath)).andExpect(method(HttpMethod.GET)).andRespond(withServerError());
    }

    String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

}
