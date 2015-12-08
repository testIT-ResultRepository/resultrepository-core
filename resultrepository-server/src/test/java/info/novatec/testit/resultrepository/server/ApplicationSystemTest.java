package info.novatec.testit.resultrepository.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.rest.controller.v1.ImportController;
import info.novatec.testit.resultrepository.server.Application.ApplicationConfiguration;
import info.novatec.testit.resultrepository.server.api.TagsService;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
@TestPropertySource(properties = "resultrepository.persistence.data-folder=target/data")
public class ApplicationSystemTest {

    @Autowired
    ImportController importController;

    @Autowired
    TagsService tagsService;

    @Test
    public void testThatImportOfResultsWorksInGeneral() throws InterruptedException {

        // generate unique tag value
        String value = String.valueOf(System.currentTimeMillis());

        // import dummy result with unique tag
        importController.importResult(new TestGroupResultData().addTag(new TagData().setValue(value)));

        // assert that exactly one result for the unique tag is found
        assertThat(tagsService.findTestGroupResults(findTagWithTimeout(value).getId())).hasSize(1);

    }

    private TagData findTagWithTimeout(String value) throws InterruptedException {

        TagData tag = null;

        long start = System.currentTimeMillis();
        while (tag == null && (System.currentTimeMillis() - start < 5000)) {
            try {
                tag = tagsService.findByValue(value);
            } catch (DataNotFoundException e) {
                Thread.sleep(100);
            }
        }

        assertThat(tag).isNotNull();
        return tag;

    }

}
