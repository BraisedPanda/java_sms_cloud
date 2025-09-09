package com.xqy.java_sms_cloud_ai.service.impl;

import com.alibaba.cloud.ai.advisor.DocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.*;
import com.xqy.java_sms_cloud_ai.service.RagService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class RagServiceImpl implements RagService {

    private static final String indexName = "学校信息库";

    @Value("classpath:/data/school_info.docx")
    private Resource springAiResource;

    private static final String retrievalSystemTemplate = "" +
            "上下文信息如下：" +
            "------------" +
            "{question_answer_context}" +
            "------------" +
            "根据上下文和提供的历史信息，而不是先验知识，回答用户问题，如果答案不在上下文中，请告知用户无法回答该问题";


    private final ChatClient chatClient;

    private final DashScopeApi dashScopeApi;

    public RagServiceImpl(ChatClient.Builder builder, DashScopeApi dashScopeApi) {
        DocumentRetriever retriever = new DashScopeDocumentRetriever(dashScopeApi,
                DashScopeDocumentRetrieverOptions.builder().withIndexName(indexName).build());

        this.dashScopeApi = dashScopeApi;
        this.chatClient = builder
                .defaultAdvisors(new DocumentRetrievalAdvisor(retriever, retrievalSystemTemplate)).build();
    }

    @Override
    public void importDocuments() {
        String path = saveToTempFile(springAiResource);
        DocumentReader reader = new DashScopeDocumentCloudReader(path, dashScopeApi, null);
        List<Document> documents = reader.read();

        VectorStore vectorStore = new DashScopeCloudStore(dashScopeApi, new DashScopeStoreOptions(indexName));
        vectorStore.add(documents);

    }

    private String saveToTempFile(Resource springAiResource) {
        try {
            File tempFile = File.createTempFile("jianli", ".docx");
            tempFile.deleteOnExit();

            InputStream inputStream = springAiResource.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return tempFile.getAbsolutePath();
        }catch (Exception e) {

        }
        return null;
    }


    @Override
    public Flux<ChatResponse> retrieve(String message) {
        return chatClient.prompt().user(message).stream().chatResponse();
    }
}
