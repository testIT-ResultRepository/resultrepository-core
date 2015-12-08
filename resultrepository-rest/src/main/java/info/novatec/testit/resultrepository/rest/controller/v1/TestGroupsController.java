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

import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.remote.v1.TestGroupsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.TestGroupsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TestGroupsController implements TestGroupsRemoteService {

    @Autowired
    private TestGroupsService delegateService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUPS, method = RequestMethod.POST)
    public
    @ResponseBody
    TestGroupData persist(@RequestBody TestGroupData testGroup) {
        return delegateService.persist(testGroup);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUPS, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestGroupData> findAll() {
        return delegateService.findAll();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUP_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TestGroupData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUPRESULTS_OF_TESTGROUP_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestGroupResultData> findTestGroupResults(@PathVariable Long id) {
        return delegateService.findTestGroupResults(id);
    }

}
