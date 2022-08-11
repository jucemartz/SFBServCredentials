package com.lottus.sfbservice.credentials.contracts.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "ApiResponseService")
@Getter
@Builder(setterPrefix = "with")
public class ApiResponseService {

    @ApiModelProperty(required = true)
    private String id;

    @ApiModelProperty(required = true)
    private String name;

}
