package info.novatec.testit.resultrepository.rest.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.remote.v1.TestResultDetailsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.TestResultDetailsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TestResultDetailsController implements TestResultDetailsRemoteService {

    @Autowired
    private TestResultDetailsService delegateService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTRESULTDETAIL_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestResultDetailData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTRESULT_OF_TESTRESULTDETAIL_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestResultData findTestResult(@PathVariable Long id) {
        return delegateService.findTestResult(id);
    }

}
