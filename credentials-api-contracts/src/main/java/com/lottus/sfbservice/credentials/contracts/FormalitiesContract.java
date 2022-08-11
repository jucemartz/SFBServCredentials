package com.lottus.sfbservice.credentials.contracts;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
public class FormalitiesContract {

    @ApiModelProperty
    protected String formalityNumber;

    @ApiModelProperty
    protected String name;

    @ApiModelProperty(required = true)
    protected String description;

    @ApiModelProperty(required = true)
    protected String cost;

    @ApiModelProperty
    protected String status;

    @ApiModelProperty
    protected String acronym;

    @Builder(setterPrefix = "with")
    private FormalitiesContract(String formalityNumber, String name, String description,
                                String cost, String status, String acronym) {
        this.formalityNumber = formalityNumber;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.status = status;
        this.acronym = acronym;
    }
}
