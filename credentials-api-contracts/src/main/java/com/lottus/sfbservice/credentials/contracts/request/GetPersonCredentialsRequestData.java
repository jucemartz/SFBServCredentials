package com.lottus.sfbservice.credentials.contracts.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lottus.sfbservice.credentials.contracts.validators.GetPersonCredentialsRequestValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "GetPersonCredentialsRequestData")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@JsonDeserialize(builder = GetPersonCredentialsRequestData.GetPersonCredentialsRequestDataBuilder.class)
public class GetPersonCredentialsRequestData {

    @ApiModelProperty(position = 2, required = true)
    protected String school;

    @ApiModelProperty(position = 3, required = true)
    protected String affiliation;

    @ApiModelProperty(position = 4, required = true)
    protected String studentId;

    @Builder(setterPrefix = "with")
    private GetPersonCredentialsRequestData(String school, String affiliation,
                                             String studentId) {
        this.school = school;
        this.affiliation = affiliation;
        this.studentId = studentId;
        GetPersonCredentialsRequestValidator.INSTANCE.validate(this);
    }
}
