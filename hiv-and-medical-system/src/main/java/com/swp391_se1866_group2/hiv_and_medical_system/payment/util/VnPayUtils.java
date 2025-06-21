package com.swp391_se1866_group2.hiv_and_medical_system.payment.util;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class VnPayUtils {
    public static String hmacSHA512(String key, String data) {
        try {
            if (key == null || data == null) throw new IllegalArgumentException("Key or data null");
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : result) sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error generating HMAC", ex);
        }
    }

    public static String hashAllFields(Map<String, String> fields, String secretKey) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = fields.get(key);
            if (value != null && !value.isEmpty()) {
                sb.append(key).append("=").append(value);
                if (i != fieldNames.size() - 1) sb.append("&");
            }
        }
        return hmacSHA512(secretKey, sb.toString());
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        return ip != null ? ip : request.getRemoteAddr();
    }

    public static String getRandomNumber(int length) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }
}

