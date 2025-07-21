package com.example.collabo.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
    private String secret;
    private final long accessTokenValidity = 1000L * 60 * 60 * 24; // 1일
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7; // 7일

    public String getBase64Secret() {
        return secret;
    }

}