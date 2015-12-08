package info.novatec.testit.resultrepository.rest.controller.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public abstract class AbstractControllerTest {

    static final long ID = 42L;
    static final long ANOTHER_ID = 42L;

    ObjectMapper mapper = new ObjectMapper();
    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(getClassUnderTest()).build();
    }

    abstract Object getClassUnderTest();

    void performAndAssertPost(String contextPath, Object requestObject) throws Exception {
        mockMvc.perform(post(contextPath).content(asJson(requestObject)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    void performAndAssertPost(String contextPath, Object requestObject, Object expectedResponse) throws Exception {
        mockMvc.perform(post(contextPath).content(asJson(requestObject)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(asJson(expectedResponse)));
    }

    void performAndAssertGet(String contextPath, Object expectedResponse) throws Exception {
        mockMvc.perform(get(contextPath))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(asJson(expectedResponse)));
    }

    String asJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    String withId(String contextPath, long id) {
        return StringUtils.replace(contextPath, "{id}", String.valueOf(id));
    }

}
