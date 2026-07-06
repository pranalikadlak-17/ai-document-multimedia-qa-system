package com.pranali.ai_doc_qa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.pranali.ai_doc_qa.dto.Content;
import com.pranali.ai_doc_qa.dto.GeminiRequest;
import com.pranali.ai_doc_qa.dto.GeminiResponse;
import com.pranali.ai_doc_qa.dto.Part;

@Service
public class GeminiService {

	@Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    private final RestClient restClient = RestClient.create();
    
    public String askGemini(String prompt) {

        Part part = new Part(prompt);

        Content content = new Content(List.of(part));

        GeminiRequest request =
                new GeminiRequest(List.of(content));

        GeminiResponse response = restClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/"
                        + model
                        + ":generateContent?key="
                        + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GeminiResponse.class);

        if (response != null
                && response.getCandidates() != null
                && !response.getCandidates().isEmpty()
                && response.getCandidates().get(0).getContent() != null
                && response.getCandidates().get(0).getContent().getParts() != null
                && !response.getCandidates().get(0).getContent().getParts().isEmpty()) {

            return response.getCandidates()
                    .get(0)
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();
        }

        return "No response from Gemini.";
}
}
