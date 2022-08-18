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
        value = "GetCostDetailRequest"
)
@JsonPropertyOrder({"data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = GetCostDetailRequest.GetCostDetailRequestBuilder.class)
@Getter
public class GetCostDetailRequest implements ApiRequest {

    @ApiModelProperty(required = true, position = 4)
    private final GetCostDetailRequestData data;

    @Builder(setterPrefix = "with")
    private GetCostDetailRequest(GetCostDetailRequestData data) {
        ParameterValidationUtils.validateNotNull(data, "data");
        this.data = data;
    }
}
