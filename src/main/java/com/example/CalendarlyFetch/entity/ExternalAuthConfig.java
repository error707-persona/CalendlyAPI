package com.example.CalendarlyFetch.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "external_auth_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalAuthConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "external_system_id", nullable = false)
    private ExternalSystem externalSystem;

    private String authHeaderName; // Authorization, X-API-KEY
    private String authPrefix; // Bearer, Basic

    @Column(length = 2000)
    private String apiKey;

    @Column(length = 2000)
    private String apiSecret;

    private String tokenUrl;
    private String scopes;
    private Long tokenExpirySeconds;
}
