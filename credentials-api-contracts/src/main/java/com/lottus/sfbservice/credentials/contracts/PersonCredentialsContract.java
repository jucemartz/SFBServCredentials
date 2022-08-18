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
    protected String userName;

    @ApiModelProperty
    protected String email;

    @ApiModelProperty
    protected String password;

    @Builder(setterPrefix = "with")
    private PersonCredentialsContract(String userName, String email,
                                      String password) {

        this.userName = userName;
        this.email = email;
        this.password = password;

    }
}
