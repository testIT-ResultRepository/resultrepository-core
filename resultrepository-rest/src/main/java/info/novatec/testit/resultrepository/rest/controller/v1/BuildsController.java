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

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.BuildsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.BuildsService;
import info.novatec.testit.resultrepository.server.api.StatisticsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BuildsController implements BuildsRemoteService {

    @Autowired
    private BuildsService delegateService;
    @Autowired
    private StatisticsService statisticsService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILDS, method = RequestMethod.POST)
    public
    @ResponseBody
    BuildData persist(@RequestBody BuildData build) {
        return delegateService.persist(build);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILD_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    BuildData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILDJOB_OF_BUILD_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    BuildJobData findBuildJob(@PathVariable Long id) {
        return delegateService.findBuildJob(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TAGS_OF_BUILD_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TagData> findTags(@PathVariable Long id) {
        return delegateService.findTags(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUPRESULTS_OF_BUILD_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestGroupResultData> findTestGroupResults(@PathVariable Long id) {
        return delegateService.findTestGroupResults(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.STATISTIC_OF_BUILD_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    BuildResultStatistic calculateStatistic(@PathVariable Long id) {
        return statisticsService.calculateBuildResultStatistic(id);
    }

}
