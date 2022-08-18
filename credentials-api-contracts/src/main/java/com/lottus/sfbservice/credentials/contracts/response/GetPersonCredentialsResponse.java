package com.lottus.sfbservice.credentials.contracts.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lottus.sfbservice.credentials.contracts.PersonCredentialsContract;
import com.lottus.sfbservice.credentials.contracts.common.ParameterValidationUtils;
import com.lottus.sfbservice.credentials.contracts.common.response.ApiResponse;
import com.lottus.sfbservice.credentials.contracts.common.response.ApiResponsePartialResponse;
import com.lottus.sfbservice.credentials.contracts.common.response.ApiResponseService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@ApiModel(
        value = "GetPersonCredentialsResponse"
)
@JsonPropertyOrder({"service", "partialResponse", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class GetPersonCredentialsResponse implements ApiResponse {

    @ApiModelProperty(required = true, position = 1)
    private final ApiResponseService service;

    @ApiModelProperty(position = 2)
    private final ApiResponsePartialResponse partialResponse;

    @ApiModelProperty(required = true, position = 3)
    private final List<PersonCredentialsContract> data;

    @Builder(setterPrefix = "with")
    private GetPersonCredentialsResponse(ApiResponseService service,
                                  ApiResponsePartialResponse partialResponse,
                                  List<PersonCredentialsContract> data) {
        ParameterValidationUtils.validateNotNull(service, "service");
        this.service = service;
        this.partialResponse = partialResponse;
        this.data = data;

    }
}
