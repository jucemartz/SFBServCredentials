package com.lottus.sfbservice.credentials.contracts.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lottus.sfbservice.credentials.contracts.validators.GetCostDetailRequestValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "GetCostDetailRequestData")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@JsonDeserialize(builder = GetCostDetailRequestData.GetCostDetailRequestDataBuilder.class)
public class GetCostDetailRequestData {

    @ApiModelProperty(position = 2, required = true)
    protected List<String> detailCodes;

    @Builder(setterPrefix = "with")
    private GetCostDetailRequestData(List<String> detailCodes) {
        this.detailCodes = detailCodes;

        GetCostDetailRequestValidator.INSTANCE.validate(this);
    }
}
