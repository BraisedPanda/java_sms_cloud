package com.xqy.java_sms_cloud_ai.controller;

import com.xqy.java_sms_cloud_ai.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    AiService aiService;


    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return aiService.askQuestion(question);
    }

}

