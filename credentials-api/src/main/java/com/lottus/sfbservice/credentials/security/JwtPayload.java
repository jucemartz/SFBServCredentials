package com.lottus.sfbservice.credentials.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.lottus.sfbservice.credentials.dto.WebAppDto;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JwtPayload.JwtPayloadBuilder.class)
@Getter
@Builder(setterPrefix = "with")
public class JwtPayload {

    @JsonProperty
    private String email;

    @JsonProperty
    private Long studentId;

    @JsonProperty
    private String academicLevelCode;

    @JsonProperty
    private WebAppDto webApp;

    @JsonProperty
    private String studentEnrollmentNumber;
}
