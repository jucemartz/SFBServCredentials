package com.lottus.sfbservice.credentials.service;

import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequest;
import com.lottus.sfbservice.credentials.wsclient.ConsultaResponse;

public interface AzureADService {
    ConsultaResponse createUser(GetPersonCredentialsRequest personRequest) throws Exception;
}
