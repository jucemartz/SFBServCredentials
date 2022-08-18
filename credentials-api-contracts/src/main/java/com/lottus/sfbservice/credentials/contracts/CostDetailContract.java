package com.lottus.sfbservice.credentials.contracts;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
public class CostDetailContract {

    @ApiModelProperty
    protected String codeDetail;

    @ApiModelProperty
    protected BigDecimal cost;

    @Builder(setterPrefix = "with")
    private CostDetailContract(String codeDetail, BigDecimal cost) {
        this.codeDetail = codeDetail;
        this.cost = cost;
    }
}
