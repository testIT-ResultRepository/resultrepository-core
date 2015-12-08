package info.novatec.testit.resultrepository.rest.controller.v1;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.remote.v1.TestsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.TestsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TestsController implements TestsRemoteService {

    @Autowired
    private TestsService delegateService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTS, method = RequestMethod.POST)
    public
    @ResponseBody
    TestData persist(@RequestBody TestData test) {
        return delegateService.persist(test);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTS, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestData> findAll() {
        return delegateService.findAll();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TEST_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTRESULTS_OF_TEST_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestResultData> findTestResults(@PathVariable Long id) {
        return delegateService.findTestResults(id);
    }

}
