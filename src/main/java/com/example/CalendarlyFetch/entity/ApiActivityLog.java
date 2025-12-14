package com.example.CalendarlyFetch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_activity_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestId;

    private Long externalSystemId;
    private Long externalApiEndpointId;

    private String httpMethod;
    private String fullUrl;

    @Column(columnDefinition = "TEXT")
    private String requestHeaders;

    @Column(columnDefinition = "TEXT")
    private String requestBody;

    private Integer responseStatus;

    @Column(columnDefinition = "TEXT")
    private String responseBody;

    private String errorMessage;
    private Long executionTimeMs;
    private Boolean successFlag;

    private LocalDateTime createdAt;
}
