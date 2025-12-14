package com.example.CalendarlyFetch.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "external_field_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalFieldMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "external_api_endpoint_id", nullable = false)
    private ExternalApiEndpoint externalApiEndpoint;

    @Column(nullable = false)
    private String externalFieldPath; // $.user.email

    @Column(nullable = false)
    private String internalFieldName; // email

    private String dataType; // STRING, DATE
    private Boolean isRequired;
    private String defaultValue;
    private String transformationRule; // LOWERCASE, DATE_FORMAT
}
