package com.lottus.sfbservice.credentials.contracts.validators;

import com.lottus.sfbservice.credentials.contracts.common.ParameterValidationUtils;
import com.lottus.sfbservice.credentials.contracts.request.GetCostDetailRequestData;

public enum GetCostDetailRequestValidator implements ContractValidator<GetCostDetailRequestData> {

    INSTANCE;

    /**
     * Validates GetCostDetailRequestData object.
     *
     * @param contract The GetCostDetailRequestData object.
     */
    public void validate(GetCostDetailRequestData contract) {
        ParameterValidationUtils.validateNotNull(contract.getDetailCodes(), "detailCodes");
    }
}
