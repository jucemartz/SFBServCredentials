package com.lottus.sfbservice.credentials.controller;

import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_107;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_504;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.INTERNAL_ERROR;

import com.lottus.sfbservice.credentials.common.ApplicationHelper;
import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.contracts.PersonCredentialsContract;
import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequest;
import com.lottus.sfbservice.credentials.contracts.response.GetPersonCredentialsResponse;
import com.lottus.sfbservice.credentials.exception.HandledException;
import com.lottus.sfbservice.credentials.exception.ServiceException;
import com.lottus.sfbservice.credentials.service.ActiveDirectoryService;
import com.lottus.sfbservice.credentials.service.AzureADService;
import com.lottus.sfbservice.credentials.wsclient.ConsultaResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${sfbservice.credentials.api.url-paths.base-path}")
@Api(value = "The subscription operations")
public class CrendentialsController extends SecureController {

    @Autowired
    private ActiveDirectoryService activeService;

    @Autowired
    private ApplicationHelper appHelper;

    @Autowired
    private ApplicationConfiguration appConfig;

    @Autowired
    private AzureADService azureService;

    /**
     * Get Crendentials.
     *
     * @param getPersonCredentialsRequest The information specified on request.
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns Credentials when succeeds")
    })
    @PostMapping(name = "Endpoint to get credentials",
            path = "${sfbservice.credentials.api.url-paths.public-path}"
                    + "${sfbservice.credentials.api.url-paths.getsf-credentials}/v1",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetPersonCredentialsResponse> getPersonCredentials(
            @ApiParam(required = true, name = "getPersonInfoRequest",
                    value = "The request body content with person information")
            @RequestBody GetPersonCredentialsRequest getPersonCredentialsRequest) {
        try {
            PersonCredentialsContract personInfoContract = new PersonCredentialsContract();
            switch (getPersonCredentialsRequest.getData().getSchool()) {
                case "ULA":
                    System.out.println("Creación de credenciales ULA...");
                    List<String> credeULA = activeService.createUser(getPersonCredentialsRequest);
                    personInfoContract.setBannerId(credeULA.get(0));
                    personInfoContract.setUserName(credeULA.get(1));
                    personInfoContract.setPassword(credeULA.get(2));
                    personInfoContract.setEmail(credeULA.get(3));
                    break;
                case "UTC":
                    System.out.println("Creación de credenciales UTC...");
                    ConsultaResponse response = azureService.createUser(getPersonCredentialsRequest);
                    if (response.getResponseCode().toUpperCase().equals("SUCCESS")) {
                        System.out.println("Success...");
                        String separator = Pattern.quote("-");
                        String[] credeUTC = response.getResponseMessage().split(separator);
                        personInfoContract.setBannerId(getPersonCredentialsRequest.getData().getStudentId());
                        personInfoContract.setUserName(credeUTC[0]);
                        personInfoContract.setPassword(credeUTC[1]);
                        personInfoContract.setEmail(credeUTC[0]);
                    } else {
                        System.out.println("Error...");
                        personInfoContract.setCodeError(response.getResponseCode());
                        personInfoContract.setMessageError(response.getResponseMessage());
                    }
                    break;
                default:
                    new ServiceException(INTERNAL_ERROR, ERROR_107.getErrorId(),
                            "Input data error (affiliation, school, studentId).");
                    break;
            }
            List<PersonCredentialsContract> person = new ArrayList<PersonCredentialsContract>();
            person.add(personInfoContract);
            return ResponseEntity.ok(GetPersonCredentialsResponse.builder()
                    .withData(person)
                    .withService(appHelper.getApiResponseService())
                    .build());
        } catch (Exception e) {
            throw new HandledException(INTERNAL_ERROR, ERROR_504.getErrorId(),
                    appConfig.getErrorMessage(ERROR_504), e);
        }
    }
}
