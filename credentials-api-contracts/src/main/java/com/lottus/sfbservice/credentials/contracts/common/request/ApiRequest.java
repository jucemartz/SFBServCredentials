package com.lottus.sfbservice.credentials.contracts.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        value = "ApiRequest",
        description = "Top level element for every api request."
)
public interface ApiRequest {

    @ApiModelProperty(required = true)
    Object getData();

}
