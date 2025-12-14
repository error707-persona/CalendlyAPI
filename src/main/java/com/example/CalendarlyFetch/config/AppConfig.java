package com.example.CalendarlyFetch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * Defines a bean for Jackson's ObjectMapper, allowing it to be
     * injected anywhere using @Autowired or constructor injection.
     */
    @Bean
    public ObjectMapper objectMapper() {
        // You can add custom configuration here if needed,
        // but the default constructor is usually enough to start.
        return new ObjectMapper();
    }
}