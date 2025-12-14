package com.example.CalendarlyFetch.entity;

import com.example.CalendarlyFetch.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "external_system")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "system_code", nullable = false, unique = true)
    private String systemCode;

    @Column(name = "system_name", nullable = false)
    private String systemName;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type", nullable = false)
    private AuthType authType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
