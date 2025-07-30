package com.swp391_se1866_group2.hiv_and_medical_system.security.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class OtpService {
    private static final Duration OTP_TTL = Duration.ofMinutes(5);
    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender mailSender;

    public String generateAndStoreOtp(String email) {
        String otp = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        String key = "otp:" + email;
        redisTemplate.opsForValue().set(key, otp, OTP_TTL);
        return otp;
    }

    public boolean validateOtp(String email, String inputOtp) {
        String key = "otp:" + email;
        String cachedOtp = redisTemplate.opsForValue().get(key);
        if(cachedOtp == null || !cachedOtp.equals(inputOtp)) {
            return false;
        }
        redisTemplate.delete(key);
        return true;
    }

    public void sendOtpEmail(String toEmail, String otp)
            throws MessagingException, IOException {
        Resource resource = new ClassPathResource("templates/otp-email.html");
        String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        html = html.replace("{{OTP}}", otp);


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("no-reply@medcarehiv.com", "HCarePlus Support");
        helper.setTo(toEmail);
        helper.setSubject("Mã OTP đặt lại mật khẩu (5 phút)");
        helper.setText(html, true);

        mailSender.send(message);
    }

}
