package com.example.CalendarlyFetch.service;

import com.example.CalendarlyFetch.entity.ApiActivityLog;
import com.example.CalendarlyFetch.entity.ExternalApiEndpoint;
import com.example.CalendarlyFetch.entity.ExternalAuthConfig;
import com.example.CalendarlyFetch.repository.ApiActivityLogRepository;
import com.example.CalendarlyFetch.repository.ExternalAuthConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExternalApiExecutorService {

        private final ExternalAuthConfigRepository authConfigRepository;
        private final WebClient.Builder webClientBuilder;
        private final ApiActivityLogRepository apiActivityLogRepository;
        UUID newUuidObject = UUID.randomUUID();

        /**
         * Generic method to call any external API dynamically
         *
         * @param apiEndpoint
         * @param queryParams
         * @param bodyParams
         * @return
         */
        public String callApi(ExternalApiEndpoint apiEndpoint,
                        Map<String, String> queryParams,
                        Object bodyParams) {

                String uuidString = newUuidObject.toString();

                ExternalAuthConfig authConfig = authConfigRepository
                                .findByExternalSystemId(apiEndpoint.getExternalSystem().getId())
                                .orElse(null);

                WebClient webClient = webClientBuilder
                                .baseUrl(apiEndpoint.getExternalSystem().getBaseUrl())
                                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .build();

                WebClient.RequestBodySpec requestSpec = webClient
                                .method(org.springframework.http.HttpMethod.valueOf(apiEndpoint.getHttpMethod().name()))
                                .uri(uriBuilder -> {
                                        uriBuilder.path(apiEndpoint.getEndpointPath());
                                        if (queryParams != null) {
                                                queryParams.forEach(uriBuilder::queryParam);
                                        }
                                        return uriBuilder.build();
                                });

                if (authConfig != null && authConfig.getApiKey() != null) {
                        System.out.print("header_name: " + authConfig.getAuthHeaderName() + " prefix: "
                                        + authConfig.getAuthPrefix());
                        requestSpec.header(authConfig.getAuthHeaderName(),
                                        (authConfig.getAuthPrefix() != null ? authConfig.getAuthPrefix() + " " : "")
                                                        + authConfig.getApiKey());
                }

                if (bodyParams != null &&
                                (apiEndpoint.getHttpMethod() == com.example.CalendarlyFetch.enums.HttpMethodType.POST
                                                || apiEndpoint.getHttpMethod() == com.example.CalendarlyFetch.enums.HttpMethodType.PUT)) {
                        requestSpec.body(BodyInserters.fromValue(bodyParams));
                }

                try {
                        Mono<String> responseMono = requestSpec.retrieve()
                                        .bodyToMono(String.class);

                        ApiActivityLog log = new ApiActivityLog(); // Instantiate the new entity
                        log.setFullUrl(apiEndpoint.getEndpointPath());
                        log.setHttpMethod("GET");
                        // log.setRequestBody(bodyParams.toString());

                        // Check for null response body before logging
                        if (responseMono.block() != null) {
                                log.setResponseBody(responseMono.block());
                        } else {
                                log.setResponseBody("No response body received.");
                        }

                        log.setExternalSystemId(apiEndpoint.getExternalSystem().getId());
                        log.setExternalApiEndpointId(apiEndpoint.getId());
                        log.setRequestId(uuidString);
                        // log.setRequestHeaders(authConfig.getAuthHeaderName() +
                        //                 (authConfig.getAuthPrefix() != null ? authConfig.getAuthPrefix() + " " : "")
                        //                 + authConfig.getApiKey());
                        log.setResponseStatus(200);
                        log.setSuccessFlag(true);
                        log.setCreatedAt(LocalDateTime.now());

                        // Use the new repository instance to save
                        apiActivityLogRepository.save(log);

                        return responseMono.block();
                } catch (Exception e) {
                        ApiActivityLog log = new ApiActivityLog(); // Instantiate the new entity
                        log.setFullUrl(apiEndpoint.getEndpointPath());
                        log.setHttpMethod("GET");
                        log.setRequestBody(queryParams != null ? queryParams.toString() : null);
                        log.setResponseBody("No response body received.");
                        log.setExternalSystemId(apiEndpoint.getExternalSystem().getId());
                        log.setExternalApiEndpointId(apiEndpoint.getId());
                        log.setResponseStatus(500);
                        log.setSuccessFlag(false);
                        log.setCreatedAt(LocalDateTime.now());
                        log.setErrorMessage(e.toString());
                        apiActivityLogRepository.save(log);

                        return "error";

                }

        }
}
