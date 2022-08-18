package com.lottus.sfbservice.credentials.contracts.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "ApiResponsePartialResponse")
@Getter
@Builder(setterPrefix = "with")
public class ApiResponsePartialResponse {

    @ApiModelProperty(required = true)
    private String url;

    @ApiModelProperty(required = true)
    private String type;

    @ApiModelProperty(required = true)
    private Object sendData;

}
