package com.swp391_se1866_group2.hiv_and_medical_system.chat.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.chat.dto.ChatRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.chat.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest chatRequest){
        return chatService.chat(chatRequest);
    }

}
