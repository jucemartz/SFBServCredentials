package com.lottus.sfbservice.credentials.contracts;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
public class PersonCredentialsContract {

    @ApiModelProperty
    protected String bannerId;

    @ApiModelProperty
    protected String userName;

    @ApiModelProperty
    protected String email;

    @ApiModelProperty
    protected String password;

    @ApiModelProperty
    protected String codeError;

    @ApiModelProperty
    protected String messageError;

    @Builder(setterPrefix = "with")
    private PersonCredentialsContract(String bannerId, String userName, String email,
                                      String password, String codeError, String messageError) {

        this.bannerId = bannerId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.codeError = codeError;
        this.messageError = messageError;

    }
}
