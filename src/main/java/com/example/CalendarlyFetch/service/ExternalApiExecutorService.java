package com.example.CalendarlyFetch.service;

import com.example.CalendarlyFetch.entity.ExternalApiEndpoint;
import com.example.CalendarlyFetch.entity.ExternalAuthConfig;
import com.example.CalendarlyFetch.repository.ExternalAuthConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExternalApiExecutorService {

    private final ExternalAuthConfigRepository authConfigRepository;

    private final WebClient.Builder webClientBuilder;

    /**
     * Generic method to call any external API dynamically
     *
     * @param apiEndpoint External API configuration from DB
     * @param queryParams Map of query params (can be null)
     * @param bodyParams  Map or JSON body (can be null)
     * @return Raw JSON response as String
     */
    public String callApi(ExternalApiEndpoint apiEndpoint,
            Map<String, String> queryParams,
            Object bodyParams) {

        // 1️⃣ Fetch authentication config
        ExternalAuthConfig authConfig = authConfigRepository
                .findByExternalSystemId(apiEndpoint.getExternalSystem().getId())
                .orElse(null);

        // 2️⃣ Build WebClient
        WebClient webClient = webClientBuilder
                .baseUrl(apiEndpoint.getExternalSystem().getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // 3️⃣ Build request
        WebClient.RequestBodySpec requestSpec = webClient
                .method(org.springframework.http.HttpMethod.valueOf(apiEndpoint.getHttpMethod().name()))
                .uri(uriBuilder -> {
                    uriBuilder.path(apiEndpoint.getEndpointPath());
                    if (queryParams != null) {
                        queryParams.forEach(uriBuilder::queryParam);
                    }
                    return uriBuilder.build();
                });

        // 4️⃣ Add headers for authentication
        if (authConfig != null && authConfig.getApiKey() != null) {
            System.out.print("header_name: "+authConfig.getAuthHeaderName()+ " prefix: "+authConfig.getAuthPrefix());
            requestSpec.header(authConfig.getAuthHeaderName(),
                    (authConfig.getAuthPrefix() != null ? authConfig.getAuthPrefix() + " " : "")
                            + authConfig.getApiKey());
        }

        // 5️⃣ Add body if POST/PUT
        if (bodyParams != null &&
                (apiEndpoint.getHttpMethod() == com.example.CalendarlyFetch.enums.HttpMethodType.POST
                        || apiEndpoint.getHttpMethod() == com.example.CalendarlyFetch.enums.HttpMethodType.PUT)) {
            requestSpec.body(BodyInserters.fromValue(bodyParams));
        }

        // 6️⃣ Execute and return response as String (blocking for now)
        Mono<String> responseMono = requestSpec.retrieve()
                .bodyToMono(String.class);

        return responseMono.block();
    }
}
