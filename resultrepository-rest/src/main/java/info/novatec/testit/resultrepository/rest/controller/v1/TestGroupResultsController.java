package info.novatec.testit.resultrepository.rest.controller.v1;

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

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.statistics.TestGroupResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.TestGroupResultsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.StatisticsService;
import info.novatec.testit.resultrepository.server.api.TestGroupResultsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TestGroupResultsController implements TestGroupResultsRemoteService {

    @Autowired
    private TestGroupResultsService delegateService;
    @Autowired
    private StatisticsService statisticsService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUPRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestGroupResultData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUP_OF_TESTGROUPRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestGroupData findTestGroup(@PathVariable Long id) {
        return delegateService.findTestGroup(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILD_OF_TESTGROUPRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    BuildData findBuild(@PathVariable Long id) {
        return delegateService.findBuild(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TAGS_OF_TESTGROUPRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TagData> findTags(@PathVariable Long id) {
        return delegateService.findTags(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTRESULTS_OF_TESTGROUPRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestResultData> findTestResults(@PathVariable Long id) {
        return delegateService.findTestResults(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.STATISTIC_OF_TESTGROUPRESULT_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestGroupResultStatistic calculateStatistic(@PathVariable Long id) {
        return statisticsService.calculateTestGroupResultStatistic(id);
    }

}
