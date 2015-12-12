package info.novatec.testit.resultrepository.plugin.junit.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.junit.JUnitReportConverter;
import info.novatec.testit.resultrepository.junit.report.xml.Testsuite;
import info.novatec.testit.resultrepository.plugin.junit.exceptions.JUnitReportImportException;
import info.novatec.testit.resultrepository.remote.ContextPaths;
import info.novatec.testit.resultrepository.server.api.AsynchronousImportService;


@RestController
@RequestMapping(value = JUnitReportController.CONTEXT_PATH)
public class JUnitReportController {

    public static final String CONTEXT_PATH =  ContextPaths.PLUGINS + "/junit";

    @Autowired
    private JUnitReportConverter converter;
    @Autowired
    private AsynchronousImportService importService;

    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public void importJUnitResult(@RequestBody Testsuite testsuite,
        @RequestParam(value = "buildJob", required = false) String buildJob,
        @RequestParam(value = "buildNumber", required = false) Integer buildNumber) {

        long timestamp = System.currentTimeMillis();
        TestGroupResultData result = converter.convertTestsuiteToTestGroupResult(testsuite, timestamp);

        BuildData buildData = getBuildDataFrom(buildJob, buildNumber);
        if (buildData != null) {
            result.setBuild(buildData);
        }

        importService.importResult(result);

    }

    private BuildData getBuildDataFrom(String buildJob, Integer buildNumber) {
        if (buildJobWithNumber(buildJob, buildNumber)) {
            BuildJobData buildJobData = new BuildJobData().setName(buildJob);
            BuildData buildData = new BuildData().setBuildJob(buildJobData).setBuildNumber(buildNumber);
            return buildData;
        } else if (buildJobWithoutNumber(buildJob, buildNumber)) {
            throw new JUnitReportImportException("missing number for build job: " + buildJob);
        } else if (buildNumberWithoutJob(buildJob, buildNumber)) {
            throw new JUnitReportImportException("missing job for build number: " + buildNumber);
        }
        // no build information given
        return null;
    }

    private boolean buildJobWithNumber(String buildJob, Integer buildNumber) {
        return StringUtils.isNotBlank(buildJob) && buildNumber != null;
    }

    private boolean buildJobWithoutNumber(String buildJob, Integer buildNumber) {
        return StringUtils.isNotBlank(buildJob) && buildNumber == null;
    }

    private boolean buildNumberWithoutJob(String buildJob, Integer buildNumber) {
        return StringUtils.isBlank(buildJob) && buildNumber != null;
    }

}
