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
        String prompt = "Bạn là trợ lý AI chỉ hỗ trợ về hệ thống quản lý HIV và bệnh nhân. Giới hạn: Không trả lời những câu hỏi không liên quan đến HIV, bệnh lý, bệnh nhân, hoặc dữ liệu hệ thống. Nếu người dùng hỏi ngoài phạm vi, hãy trả lời: 'Xin lỗi, tôi chỉ hỗ trợ các nội dung liên quan đến HIV và hệ thống quản lý y tế.' . Câu hỏi của khách hàng: " + request.message();
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }


}
