package com.example.CalendarlyFetch.entity;

import com.example.CalendarlyFetch.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "external_api_endpoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalApiEndpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "external_system_id", nullable = false)
    private ExternalSystem externalSystem;

    @Column(nullable = false)
    private String endpointCode; // LIST_USERS

    private String endpointName;

    @Column(nullable = false)
    private String requestContentType;

    @Column(nullable = false)
    private String responseContentType;

    @Column(nullable = false)
    private String headerName;

    @Enumerated(EnumType.STRING)
    private HttpMethodType httpMethod;

    @Column(nullable = false)
    private String endpointPath;

    @Column(columnDefinition = "TEXT")
    private String queryParamsTemplate;

    @Column(columnDefinition = "TEXT")
    private String headersTemplate;

    @Column(columnDefinition = "TEXT")
    private String requestBodyTemplate;

    private String responseRootPath;

    @Enumerated(EnumType.STRING)
    private PaginationType paginationType;

    @Enumerated(EnumType.STRING)
    private Status status;
}
