package com.lottus.sfbservice.credentials.contracts.validators;

public interface ContractValidator<T> {
    void validate(T object);
}
