package com.xqy.java_sms_cloud_ai.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
@RequestMapping("/chat-memory")
public class ChatMemoryController {

    private final ChatClient chatClient;

    private static final String DEFAULT_PROMPT = "你是一个导游，请根据用户提问回答！";

    public ChatMemoryController(ChatModel chatModel) {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultSystem(DEFAULT_PROMPT)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }


    @GetMapping("/in-memory")
    public Flux<String> inMemory(@RequestParam String prompt, @RequestParam String charId, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatClient.prompt(prompt).advisors(
                a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, charId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
        ).stream().content();
    }

}
