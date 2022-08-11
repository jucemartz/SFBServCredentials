package com.lottus.sfbservice.credentials.contracts.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;

@ApiModel(
        value = "ApiErrorResponse",
        description = "Top level element for every api error response."
)
@Getter
@Builder(setterPrefix = "with")
public class ApiErrorResponse {

    @ApiModelProperty(required = true)
    private ApiResponseService service;

    @ApiModelProperty(required = true)
    private ApiResponseStatus status;

    @ApiModelProperty(required = true)
    private ApiResponseError error;
}
