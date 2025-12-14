package com.example.CalendarlyFetch.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Service
public class HuggingFaceClient {

    private final String apiToken;
    private final String modelUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public HuggingFaceClient(
        @Value("${huggingface.api.token}") String apiToken,
        @Value("${huggingface.model.url}") String modelUrl) {
        
        this.apiToken = apiToken;
        this.modelUrl = modelUrl;
    }

    public String callLLM(String model, String userMessage) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);

        List<Map<String,String>> messages = new ArrayList<>();
        Map<String,String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", userMessage);
        messages.add(msg);

        Map<String,Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", messages);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(modelUrl + "/chat/completions", request, Map.class);

        if(response.getBody() != null) {
            @SuppressWarnings("unchecked")
            List<Map<String,Object>> choices = (List<Map<String,Object>>) response.getBody().get("choices");
            if(choices != null && !choices.isEmpty()) {
                @SuppressWarnings("unchecked")
                Map<String,Object> message = (Map<String,Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        }

        return "No response from model";
    }
}
