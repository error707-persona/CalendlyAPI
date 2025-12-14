package com.example.CalendarlyFetch.controller;

import com.example.CalendarlyFetch.entity.ExternalApiEndpoint;
import com.example.CalendarlyFetch.repository.ExternalApiEndpointRepository;
import com.example.CalendarlyFetch.service.ExternalApiExecutorService;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ApiTestController {

    private final ExternalApiExecutorService executorService;
    private final ExternalApiEndpointRepository endpointRepository;
    private final ObjectMapper objectMapper;

    @GetMapping("/test-api")
    public String testCall(Model model) {
        ExternalApiEndpoint endpoint = endpointRepository.findById(3L).orElse(null);
        if (endpoint == null)
            return "Endpoint not found";

        Map<String, String> queryParams = Map.of("status", "active");
        String jsonResponse = executorService.callApi(endpoint, queryParams, null);
        try {
            // The code that can throw the exception
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode user = root.get("resource");

            model.addAttribute("user", user);
            return "calendly-user"; // HTML file name

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            // Handle the error: log it and return an error page/message
            e.printStackTrace(); // Log the error for debugging
            model.addAttribute("error", "Failed to process API response: Invalid JSON format.");
            return "error-view"; // Make sure you have an 'error-view.html' template
        }
    }
}
