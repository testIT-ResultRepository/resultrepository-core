package info.novatec.testit.resultrepository.rest.controller.v1;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.statistics.TestResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.TestResultsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.StatisticsService;
import info.novatec.testit.resultrepository.server.api.TestResultsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TestResultsController implements TestResultsRemoteService {

    @Autowired
    private TestResultsService delegateService;
    @Autowired
    private StatisticsService statisticsService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestResultData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TEST_OF_TESTRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestData findTest(@PathVariable Long id) {
        return delegateService.findTest(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUPRESULT_OF_TESTRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestGroupResultData findTestGroupResult(@PathVariable Long id) {
        return delegateService.findTestGroupResult(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TAGS_OF_TESTRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TagData> findTags(@PathVariable Long id) {
        return delegateService.findTags(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTRESULTDETAILS_OF_TESTRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    List<TestResultDetailData> findTestResultDetails(@PathVariable Long id) {
        return delegateService.findTestResultDetails(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.STATISTIC_OF_TESTRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestResultStatistic calculateStatistic(@PathVariable Long id) {
        return statisticsService.calculateTestResultStatistic(id);
    }

}
