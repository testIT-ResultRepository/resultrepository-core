package info.novatec.testit.resultrepository.rest.controller.v1;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.remote.v1.TagsRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.remote.v1.descriptors.TagDescriptor;
import info.novatec.testit.resultrepository.server.api.TagsService;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TagsController implements TagsRemoteService {

    @Autowired
    private TagsService delegateService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TAGS, method = RequestMethod.POST)
    public
    @ResponseBody
    TagData persist(@RequestBody TagData tag) {
        return delegateService.persist(tag);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TAGS, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TagData> findAll() {
        return delegateService.findAll();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TAGS_SEARCH, method = RequestMethod.POST)
    public
    @ResponseBody
    Set<TagData> findMatching(@RequestBody TagDescriptor descriptor) {

        if (descriptor.isUseEquals()) {
            Set<TagData> set = new HashSet<>();
            set.add(delegateService.findByValue(descriptor.getPattern()));
            return set;
        }

        return delegateService.findAll()
            .stream()
            .filter(tag -> tag.getValue().matches(descriptor.getPattern()))
            .collect(Collectors.toSet());

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TAG_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    TagData findById(@PathVariable Long id) {
        return delegateService.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.BUILDS_OF_TAG_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<BuildData> findBuilds(@PathVariable Long id) {
        return delegateService.findBuilds(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTGROUPRESULTS_OF_TAG_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestGroupResultData> findTestGroupResults(@PathVariable Long id) {
        return delegateService.findTestGroupResults(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.TESTRESULTS_OF_TAG_FOR_ID, method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TestResultData> findTestResults(@PathVariable Long id) {
        return delegateService.findTestResults(id);
    }

}
