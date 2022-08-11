package com.lottus.sfbservice.credentials.contracts.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lottus.sfbservice.credentials.contracts.common.ParameterValidationUtils;
import com.lottus.sfbservice.credentials.contracts.common.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(
        value = "GetPersonCredentialsRequest"
)
@JsonPropertyOrder({"data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = GetPersonCredentialsRequest.GetPersonCredentialsRequestBuilder.class)
@Getter
public class GetPersonCredentialsRequest implements ApiRequest {

    @ApiModelProperty(required = true, position = 4)
    private final GetPersonCredentialsRequestData data;

    @Builder(setterPrefix = "with")
    private GetPersonCredentialsRequest(GetPersonCredentialsRequestData data) {
        ParameterValidationUtils.validateNotNull(data, "data");
        this.data = data;
    }
}
