package com.lottus.sfbservice.credentials.contracts.validators;

import com.lottus.sfbservice.credentials.contracts.common.ParameterValidationUtils;
import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequestData;

public enum GetPersonCredentialsRequestValidator implements PersonValidator<GetPersonCredentialsRequestData> {

    INSTANCE;

    /**
     * Validates GetPersonCredentialsRequestData object.
     *
     * @param contract The GetPersonCredentialsRequestData object.
     */

    public void validate(GetPersonCredentialsRequestData contract) {
        ParameterValidationUtils.validateNotNull(contract.getSchool(),"school");
        ParameterValidationUtils.validateNotNull(contract.getAffiliation(),"affiliation");
        ParameterValidationUtils.validateNotNull(contract.getStudentId(),"studentId");
        ParameterValidationUtils.validateNotNull(contract.getProcess(),"process");
    }
}
