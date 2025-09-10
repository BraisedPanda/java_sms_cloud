package com.xqy.java_sms_cloud_ai.controller;

import com.xqy.java_sms_cloud_ai.tool.weather.tool.UserInfoTool;
import com.xqy.java_sms_cloud_ai.tool.weather.tool.WeatherToolImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {



    private final ChatClient chatClient;

    @Autowired
    private UserInfoTool userInfoTool;

    public WeatherController(ChatClient.Builder builder) {
        this.chatClient = builder
//                .defaultFunctions("weatherFunction")
                .defaultTools(new WeatherToolImpl())
                .build();

    }

    @GetMapping("/chat")
    public String simpleChat(@RequestParam(value = "input") String input) {
        return chatClient.prompt().user(input)
                .call()
                .content();
    }

    @GetMapping("/chat-tool-function")
    public String functionChat(@RequestParam(value = "input") String input) {
        return chatClient.prompt().user(input)
//                .functions("weatherFunction")
                .tools(new WeatherToolImpl())
                .call()
                .content();
    }

    @GetMapping("/chat-user-toll")
    public String functionChat2(@RequestParam(value = "input") String input) {
        return chatClient.prompt().user(input)
//                .functions("weatherFunction")
                .tools(userInfoTool )
                .call()
                .content();
    }


}
