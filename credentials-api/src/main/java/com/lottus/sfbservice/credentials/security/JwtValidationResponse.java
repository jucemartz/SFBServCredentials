package com.lottus.sfbservice.credentials.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Getter;

@JsonPropertyOrder({"data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JwtValidationResponse.JwtValidationResponseBuilder.class)
@Getter
@Builder(setterPrefix = "with")
public class JwtValidationResponse {

    @JsonProperty
    private Boolean authorized;
}
