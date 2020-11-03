package com.poogle.phog.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poogle.phog.domain.User;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String APPLE_KEY;
    private final String APPLE_ID;

    public JwtService(Environment env) {
        APPLE_KEY = env.getProperty("APPLE_KEY");
        APPLE_ID = env.getProperty("APPLE_ID");
    }

    public Map<String, Object> getTokenPayload(String jwt) {
        Map<String, Object> payloadMap = new HashMap<>();
        ObjectMapper om = new ObjectMapper();
        String encodedTokenPayload = jwt.split("\\.")[1];
        Base64.Decoder decoder = Base64.getDecoder();
        String tokenPayload = new String(decoder.decode(encodedTokenPayload));
        try {
            payloadMap = om.readValue(tokenPayload, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            System.out.println("[getTokenPayLoad] " + e.getMessage());
        }
        return payloadMap;
    }

    public boolean checkAppleValidation(Map<String, Object> payloads) {
        //APPLE_KEY
        String registeredKey = (String) payloads.get("iss");
        //APPLE_ID
        String account = (String) payloads.get("aud");
        return (registeredKey.equals(APPLE_KEY) && account.equals(APPLE_ID));
    }

    public User parseAppleJwt(Map<String, Object> payloads) {
        String socialId = (String) payloads.get("sub");
        String email = (String) payloads.get("email");
        String name = email.substring(0, email.indexOf("@"));
        return User.builder()
                .name(name)
                .socialId(socialId)
                .email(email)
                .build();
    }
}
