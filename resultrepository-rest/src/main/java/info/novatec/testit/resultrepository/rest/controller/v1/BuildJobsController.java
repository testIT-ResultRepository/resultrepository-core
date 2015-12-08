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
import info.novatec.testit.resultrepository.remote.v1.BuildJobsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.BuildJobsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BuildJobsController implements BuildJobsRemoteService {

    @Autowired
    private BuildJobsService delegateService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILDJOBS, method = RequestMethod.POST)
    public
    @ResponseBody
    BuildJobData persist(@RequestBody BuildJobData buildJob) {
        return delegateService.persist(buildJob);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILDJOBS, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<BuildJobData> findAll() {
        return delegateService.findAll();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILDJOB_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    BuildJobData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILDS_OF_BUILDJOB_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<BuildData> findBuilds(@PathVariable Long id) {
        return delegateService.findBuilds(id);
    }

}
