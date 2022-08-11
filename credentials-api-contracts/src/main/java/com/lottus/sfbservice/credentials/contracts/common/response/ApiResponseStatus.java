package com.lottus.sfbservice.credentials.contracts.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "ApiResponseStatus")
@Getter
@Builder(setterPrefix = "with")
public class ApiResponseStatus {

    @ApiModelProperty(required = true)
    private String id;

    @ApiModelProperty(required = true)
    private String info;

}
