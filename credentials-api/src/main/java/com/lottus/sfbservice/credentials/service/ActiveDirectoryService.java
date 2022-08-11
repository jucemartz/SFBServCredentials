package com.lottus.sfbservice.credentials.service;

import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequest;

import java.util.List;

public interface ActiveDirectoryService {
    List<String> createUser(GetPersonCredentialsRequest personRequest) throws Exception;
}
