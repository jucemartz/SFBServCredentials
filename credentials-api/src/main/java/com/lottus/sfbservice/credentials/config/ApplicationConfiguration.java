package com.lottus.sfbservice.credentials.config;

import com.lottus.sfbservice.credentials.enums.ApplicationErrorCode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationConfiguration {

    @Value("${sfbservice.credentials.api.id}")
    private String apiId;

    @Value("${sfbservice.credentials.api.name}")
    private String apiName;

    @Value("${sfbservice.credentials.api.version}")
    private String apiVersion;

    @Value("${sfbservice.credentials.api.url-paths.base-path}")
    private String inscripcionApiPath;

    @Value("${sfbservice.credentials.api.url-paths.public-path}")
    private String publicPath;

    @Value("${sfbservice.security.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Value("${sfbservice.credentials.api.url-paths.formalities}")
    private String formalities;

    @Value("${sfbservice.credentials.api.url-paths.getsf-cost}")
    private String costDetail;

    @Value("${sfbservice.credentials.api.url-paths.getsf-credentials}")
    private String personCredentials;

    @Getter(AccessLevel.NONE)
    private final Properties errors;

    private ApplicationConfiguration() throws IOException {
        this.errors = new Properties();
        ClassPathResource resource = new ClassPathResource("errors.properties");
        try (final var in = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            this.errors.load(in);
        }
    }

    /**
     * Return the specified Error message from properties.
     *
     * @param errorCode The ApplicationErrorCode to retrieve from properties.
     */
    public String getErrorMessage(ApplicationErrorCode errorCode) {
        return (String) this.errors.get(errorCode.getErrorId());
    }

}
