package com.graphql.sample.framework.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
@Getter
@RequiredArgsConstructor
public class SecurityProperties {

    private final String tokenSecret;

    private final String tokenIssuer;

    private final Duration tokenExpiration = Duration.ofHours(12);
}
