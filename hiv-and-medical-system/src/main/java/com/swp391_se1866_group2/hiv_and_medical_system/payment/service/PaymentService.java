package com.swp391_se1866_group2.hiv_and_medical_system.payment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.payment.entity.Payment;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.util.VnPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
     Payment vnPayProperties;

    public PaymentService(Payment vnPayProperties) {
        this.vnPayProperties = vnPayProperties;
    }

    public String createPaymentUrl(long amount, String bankCode, HttpServletRequest request) {
        try {
            String vnpVersion = "2.1.0";
            String vnpCommand = "pay";
            String orderType = "other";
            String vnp_TxnRef = VnPayUtils.getRandomNumber(8);
            String vnp_IpAddr = VnPayUtils.getClientIp(request);
            String vnp_TmnCode = vnPayProperties.getTmnCode();

            String orderInfo = "Thanh toan don hang " + vnp_TxnRef; // Tránh ký tự đặc biệt
            String vnp_ReturnUrl = vnPayProperties.getReturnUrl();
            String vnp_Amount = String.valueOf(amount * 100);
            String locale = "vn";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String vnp_CreateDate = LocalDateTime.now().format(formatter);

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnpVersion);
            vnp_Params.put("vnp_Command", vnpCommand);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", vnp_Amount);
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", orderInfo);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", locale);
            vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }

            // Bước 1: Sắp xếp key theo thứ tự A-Z
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);

            // Bước 2: Tạo hashData (không mã hóa, bỏ tham số rỗng)
            StringBuilder hashData = new StringBuilder();
            for (int i = 0; i < fieldNames.size(); i++) {
                String fieldName = fieldNames.get(i);
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    hashData.append(fieldName).append("=").append(fieldValue);
                    if (i < fieldNames.size() - 1) {
                        hashData.append("&");
                    }
                }
            }

            // Ghi log để kiểm tra
            System.out.println("Hash Data: " + hashData.toString());

            // Bước 3: Tạo chữ ký bằng HmacSHA512
            String secureHash = VnPayUtils.hmacSHA512(vnPayProperties.getSecretKey(), hashData.toString());

            // Bước 4: Tạo URL với tham số đã mã hóa
            StringBuilder query = new StringBuilder();
            for (int i = 0; i < fieldNames.size(); i++) {
                String fieldName = fieldNames.get(i);
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                            .append("=")
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    if (i < fieldNames.size() - 1) {
                        query.append("&");
                    }
                }
            }

            // Bước 5: Thêm secureHash vào URL
            query.append("&vnp_SecureHash=").append(secureHash);

            // Ghi log URL cuối cùng
            String finalUrl = vnPayProperties.getPayUrl() + "?" + query.toString();
            System.out.println("Final URL: " + finalUrl);

            return finalUrl;

        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo URL VNPay", e);
        }
    }
}



