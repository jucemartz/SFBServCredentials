package com.lottus.sfbservice.credentials.contracts.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        value = "ApiResponse",
        description = "Top level element for every api response."
)
public interface ApiResponse {

    @ApiModelProperty(required = true)
    ApiResponseService getService();

    @ApiModelProperty
    ApiResponsePartialResponse getPartialResponse();

    @ApiModelProperty
    Object getData();

}
