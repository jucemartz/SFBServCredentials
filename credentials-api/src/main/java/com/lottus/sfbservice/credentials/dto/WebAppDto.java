package com.lottus.sfbservice.credentials.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(setterPrefix = "with")
public class WebAppDto {

    private String url;

    private String school;

}
