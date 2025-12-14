package com.example.CalendarlyFetch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.CalendarlyFetch.service.HuggingFaceClient;

@Controller
public class HomeController {

    @Autowired
    private HuggingFaceClient hfClient;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/generate")
    @ResponseBody
    public String generate(@RequestParam String docsUrl) {
        String model = "moonshotai/Kimi-K2-Instruct-0905";
        System.out.print("Received URL: " + docsUrl);
        String prompt = "Read the docs of the provided url and generate api calls for all endpoints available on this site "
                + docsUrl;
        // String response = hfClient.callLLM(model, prompt);
        // System.out.println("final response: " + response);
        return "";
    }
}
