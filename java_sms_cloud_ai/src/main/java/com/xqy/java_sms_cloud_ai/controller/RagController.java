package com.xqy.java_sms_cloud_ai.controller;

import com.xqy.java_sms_cloud_ai.service.RagService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rag")
public class RagController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private VectorStore vectorStore;


    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }


    @GetMapping(value = "/chat", produces = "text/plain; chatset=UFT-8")
    public String generation(String input) {
        return chatClient.prompt().user(input).advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }

    @GetMapping(value = "/importDocument")
    public void importDocument() {
        ragService.importDocuments();
    }

    @GetMapping(value = "/queryDocument")
    public Flux<String>  queryDocument(@RequestParam(value = "message", defaultValue = "请问你知识库文档是关于什么内容的") String message) {
       return ragService.retrieve(message).map(x -> x.getResult().getOutput().getText());
    }

}
