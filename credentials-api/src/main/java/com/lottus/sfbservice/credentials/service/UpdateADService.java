package com.lottus.sfbservice.credentials.service;

import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequest;

import java.util.List;

public interface UpdateADService {
    List<String> updateUser(GetPersonCredentialsRequest personRequest) throws Exception;
}
