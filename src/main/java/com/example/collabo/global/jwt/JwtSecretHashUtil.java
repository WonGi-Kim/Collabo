package com.example.collabo.global.jwt;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtSecretHashUtil {

    public String hashSecret(byte[] secretKey, String algorithm) throws Exception {
        Mac hmac = Mac.getInstance(algorithm); // HmacSHA256 or HmacSHA512
        SecretKeySpec keySpec = new SecretKeySpec(secretKey, algorithm);
        hmac.init(keySpec);
        byte[] hashedBytes = hmac.doFinal("jwt-secret".getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public SecretKey getSecretKey(String base64Secret, String algorithm) {
        try {
            byte[] decodedSecret = Base64.getDecoder().decode(base64Secret);

            // 단순화된 방식
            return new SecretKeySpec(decodedSecret, algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create secret key", e);
        }
    }
}
