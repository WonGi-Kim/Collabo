package com.example.collabo.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class JwtConfig {
    @Value("${jwt.accessSecret}")
    private String accessSecret;
    private long accessTokenValidity = 1000L * 60 * 60 * 24; // 1일
    private long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7; // 7일

    public String getBase64Secret() {
        return accessSecret;
    }
}