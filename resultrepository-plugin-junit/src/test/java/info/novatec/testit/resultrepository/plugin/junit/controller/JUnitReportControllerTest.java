package info.novatec.testit.resultrepository.plugin.junit.controller;

import static info.novatec.testit.resultrepository.plugin.junit.controller.JUnitReportController.CONTEXT_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import info.novatec.testit.resultrepository.junit.JUnitReportConverter;
import info.novatec.testit.resultrepository.server.api.AsynchronousImportService;


@RunWith(MockitoJUnitRunner.class)
public class JUnitReportControllerTest {

    @Mock
    JUnitReportConverter converter;
    @Mock
    AsynchronousImportService importService;

    @InjectMocks
    JUnitReportController cut;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
    }

    @Test
    public void importJUnitReport() throws Exception {
        String testXml = readResource("TEST-foo.Test.xml");
        mockMvc.perform(post(CONTEXT_PATH).content(testXml).contentType(MediaType.APPLICATION_XML))
            .andExpect(status().isAccepted());
    }

    @Test
    public void importMalformedXml() throws Exception {
        String testXml = "<malformed>not a test suite</malformed>";
        mockMvc.perform(post(CONTEXT_PATH).content(testXml).contentType(MediaType.APPLICATION_XML))
            .andExpect(status().isBadRequest());
    }

    String readResource(String resourceName) throws URISyntaxException, IOException {
        URI uri = getClass().getClassLoader().getResource(resourceName).toURI();
        return Files.lines(Paths.get(uri), Charset.forName("UTF-8")).collect(Collectors.joining("\n"));
    }

}
