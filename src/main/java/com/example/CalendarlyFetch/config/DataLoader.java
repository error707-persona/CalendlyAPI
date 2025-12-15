package com.example.CalendarlyFetch.config;

import com.example.CalendarlyFetch.entity.*;
import com.example.CalendarlyFetch.enums.AuthType;
import com.example.CalendarlyFetch.enums.HttpMethodType;
import com.example.CalendarlyFetch.enums.Status;
import com.example.CalendarlyFetch.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

// NOTE: Ensure you replace 'com.example.CalendarlyFetch' with your actual package structure.

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final ExternalSystemRepository systemRepository;
    private final ExternalApiEndpointRepository endpointRepository;
    private final ExternalAuthConfigRepository authConfigRepository;
    private final ApiActivityLogRepository activityLogRepository;

    // UUID for the complex log entry (simulated)
    private final UUID SAMPLE_UUID = UUID.fromString("cc94ebe3-3f47-4d0c-9da4-70153347adc6");

    // JSON response string from api_activity_log.csv snippet (simplified for
    // insertion)
    private static final String SAMPLE_RESPONSE_JSON = "{\"resource\":{\"avatar_url\":null,\"created_at\":\"2025-12-14T11:27:34.798000Z\","
            +
            "\"current_organization\":\"https://api.calendly.com/organizations/861d62d9-7ea7-44c3-a2d2-9291640809ce\","
            +
            "\"email\":\"areeshasayed786@gmail.com\",\"locale\":\"en\",\"name\":\"Areesha Sayed\"," +
            "\"resource_type\":\"User\",\"scheduling_url\":\"https://calendly.com/areeshasayed786\"," +
            "\"slug\":\"areeshasayed786\",\"time_notation\":\"12h\",\"timezone\":\"Asia/Calcutta\"," +
            "\"updated_at\":\"2025-12-14T13:24:19.836262Z\",\"uri\":\"https://api.calendly.com/users/b4e9290a-9cf1-46e8-867d-50ef090be166\"}}";

    @Bean
    // @Transactional
    public CommandLineRunner loadData() {
        return args -> {
            System.out.println("--- Starting Database Seeding ---");
            try {
                // 1. ExternalSystem
                ExternalSystem system = new ExternalSystem();
                system.setId(1L);
                system.setAuthType(AuthType.API_KEY);
                system.setBaseUrl("https://api.calendly.com");
                system.setStatus(Status.ACTIVE);
                system.setSystemCode("CALENDLY");
                system.setSystemName("Calendly");
                systemRepository.save(system);
                System.out.println("Seeded ExternalSystem: " + system.getSystemName());

                // 2. ExternalApiEndpoint
                ExternalApiEndpoint endpoint = new ExternalApiEndpoint();
                endpoint.setId(1L);
                endpoint.setEndpointName("Get Current session User");
                endpoint.setEndpointPath("/users/me");
                endpoint.setHttpMethod(HttpMethodType.GET);
                endpoint.setStatus(Status.ACTIVE);
                endpoint.setRequestContentType("application/json");
                endpoint.setResponseContentType("application/json");
                endpoint.setHeaderName("Authorization");
                // Headers JSON (simplified representation)
                endpoint.setHeadersTemplate(
                        "{\"Authorization\":\"Bearer {{ACCESS_TOKEN}}\",\"Content-Type\":\"application/json\"}");
                endpointRepository.save(endpoint);
                System.out.println("Seeded ExternalApiEndpoint: " + endpoint.getEndpointName());

                // 3. ExternalAuthConfig (Partial data due to large encoded string)
                ExternalAuthConfig authConfig = new ExternalAuthConfig();
                authConfig.setId(1L);
                authConfig.setApiSecret("eyJraWQiOiIxY2UxZTEzNjE3ZGNmNzY2Yj... (truncated)");
                authConfig.setApiKey("voqqP2oVbQMZrm7U87ZWMjuFwLozKhDj_yCht08V3Yc");
                authConfig.setAuthHeaderName("Authorization");
                authConfig.setAuthPrefix("Bearer");
                authConfig.setTokenUrl("https://auth.calendly.com/oauth/token");
                authConfig.setScopes("7LDj4EH2Ea7Mg6zKI5rwuH5xS70gvdQRjS5V7ovFA6c");
                authConfigRepository.save(authConfig);
                System.out.println("Seeded ExternalAuthConfig for System ID: " + authConfig.getSystemId());

                // 5. ApiActivityLog (using the second log entry from the CSV snippet)
                ApiActivityLog log = new ApiActivityLog();
                // Note: Assuming ID is auto-generated, not set manually if identity strategy is
                // used.
                log.setResponseStatus(200);
                log.setCreatedAt(LocalDateTime.parse("2025-12-15T00:57:34.432814"));
                log.setExternalApiEndpointId(1L);
                log.setFullUrl("/users/me");
                log.setHttpMethod("GET");
                log.setRequestBody(SAMPLE_RESPONSE_JSON);
                activityLogRepository.save(log);
                System.out.println("Seeded ApiActivityLog with status: " + log.getResponseStatus());
            } catch (Exception e) {
                System.err.println("⚠️ Data seeding failed, but application will continue.");
                e.printStackTrace();
            }
            

            System.out.println("--- Database Seeding Complete ---");
        };
    }
}