package com.swp391_se1866_group2.hiv_and_medical_system.chat.service;

import com.swp391_se1866_group2.hiv_and_medical_system.chat.dto.ChatRequest;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    public String chat(ChatRequest request) {
        String prompt = "Bạn là trợ lý AI chỉ hỗ trợ về hệ thống quản lý HIV và bệnh nhân. Giới hạn: Chỉ trả lời các câu hỏi liên quan đến căn bệnh HIV, bệnh lý, bệnh nhân, phương pháp điều trị, ARV, các loại thuốc điều trị HIV, hoặc dữ liệu liên quan đến căn bệnh HIV. Nếu người dùng hỏi ngoài phạm vi, hãy trả lời: 'Xin lỗi, tôi chỉ hỗ trợ các nội dung liên quan đến HIV và hệ thống quản lý y tế.' Hãy suy nghĩ nhanh và ít lại và trả lời ngắn gọn đúng trọng tâm chính. Câu hỏi của khách hàng: " + request.message();
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }


}
