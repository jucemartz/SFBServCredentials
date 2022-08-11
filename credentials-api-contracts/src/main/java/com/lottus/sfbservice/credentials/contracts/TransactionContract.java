package com.lottus.sfbservice.credentials.contracts;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "TransactionContract")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class TransactionContract {

    @ApiModelProperty(required = true)
    private final Long transactionNumber;

    @ApiModelProperty(required = true)
    private final String conceptCode;

    @ApiModelProperty(required = true)
    private final String conceptDescription;

    @ApiModelProperty(required = true)
    private final String periodCode;

    @ApiModelProperty(required = true)
    private final String periodDescription;

    @ApiModelProperty(required = true)
    private final String dueDate;

    @ApiModelProperty(required = true)
    private final Double balance;

    @ApiModelProperty(required = true)
    private final Boolean canBeCancelled;

    @Builder(setterPrefix = "with")
    private TransactionContract(Long transactionNumber, String conceptCode, String conceptDescription,
                                String periodCode, String periodDescription, String dueDate, Double balance,
                                Boolean canBeCancelled) {
        this.transactionNumber = transactionNumber;
        this.conceptCode = conceptCode;
        this.conceptDescription = conceptDescription;
        this.periodCode = periodCode;
        this.periodDescription = periodDescription;
        this.dueDate = dueDate;
        this.balance = balance;
        this.canBeCancelled = canBeCancelled;
    }

}
