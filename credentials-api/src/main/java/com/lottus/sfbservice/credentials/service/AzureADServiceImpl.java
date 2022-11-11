package com.lottus.sfbservice.credentials.service;

import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_107;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_503;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.INTERNAL_ERROR;

import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequest;
import com.lottus.sfbservice.credentials.exception.ServiceException;
import com.lottus.sfbservice.credentials.wsclient.ConsultaResponse;
import com.lottus.sfbservice.credentials.wsclient.CreateUserAd;
import com.lottus.sfbservice.credentials.wsclient.CreateUserAdResponse;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class AzureADServiceImpl implements AzureADService {

    InputStream inputStream;

    @Autowired
    private ApplicationConfiguration appConfig;

    private final Logger logger = LoggerFactory.getLogger(AzureADServiceImpl.class);

    @Override
    public ConsultaResponse createUser(GetPersonCredentialsRequest personRequest) throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        Properties prop = new Properties();
        String propFileName = "application.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            logger.error("property file '" + propFileName + "' not found in the classpath");
            new ServiceException(INTERNAL_ERROR, ERROR_107.getErrorId(),
                    "property file '" + propFileName + "' not found in the classpath");
        }
        String path = prop.getProperty("azure.ws.path");
        String endpoint = prop.getProperty("azure.ws.endpoint");
        String soapaction = prop.getProperty("azure.ws.soapaction");
        marshaller.setContextPath(path);
        WebServiceTemplate template = new WebServiceTemplate(marshaller);
        CreateUserAd request = new CreateUserAd();
        ConsultaResponse response = new ConsultaResponse();
        switch (personRequest.getData().getAffiliation().toUpperCase()) {
            case "FACULTY": {
                logger.info("Creación de Credenciales para docente");
                request.setMatricula(personRequest.getData().getStudentId());
                request.setTipoUsuario("Docente");
                CreateUserAdResponse createUser =
                        (CreateUserAdResponse) template.marshalSendAndReceive(endpoint,
                                request, new SoapActionCallback(soapaction));
                response = createUser.getCreateUserAdResult();
                break;
            }
            case "STUDENT": {
                logger.info("Creacion de credenciales para Estudiante");
                request.setMatricula(personRequest.getData().getStudentId());
                request.setTipoUsuario("Alumno");
                CreateUserAdResponse createUser =
                        (CreateUserAdResponse) template.marshalSendAndReceive(endpoint,
                                request, new SoapActionCallback(soapaction));
                response = createUser.getCreateUserAdResult();
                break;
            }
            default:
                logger.error(appConfig.getErrorMessage(ERROR_503));
                new ServiceException(INTERNAL_ERROR, ERROR_503.getErrorId(),
                        appConfig.getErrorMessage(ERROR_503));
                break;
        }
        return response;
    }
}

