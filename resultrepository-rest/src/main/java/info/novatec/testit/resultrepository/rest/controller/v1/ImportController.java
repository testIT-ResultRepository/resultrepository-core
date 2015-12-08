package info.novatec.testit.resultrepository.rest.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.remote.v1.ImportRemoteService;
import info.novatec.testit.resultrepository.remote.v1.V1ContextPaths;
import info.novatec.testit.resultrepository.server.api.AsynchronousImportService;


@RestController
public class ImportController implements ImportRemoteService {

    @Autowired
    private AsynchronousImportService importService;

    @Override
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = V1ContextPaths.IMPORT_TEST_GROUP_RESULT, method = RequestMethod.POST)
    public void importResult(@RequestBody TestGroupResultData result) {
        importService.importResult(result);
    }

}
