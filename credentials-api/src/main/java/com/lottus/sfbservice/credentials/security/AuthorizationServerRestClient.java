package com.lottus.sfbservice.credentials.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AuthorizationServerRestClient",
        url = "${sfbservice.security.oauth2.authorization-server.uri}")
public interface AuthorizationServerRestClient {


    /**
     * Validates an access token.
     *
     * @param authorization  The authorization header value containing the access token.
     * @return {@link JwtValidationResponse} object.
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    JwtValidationResponse validateAccessToken(@RequestHeader("Authorization") String authorization);

}
