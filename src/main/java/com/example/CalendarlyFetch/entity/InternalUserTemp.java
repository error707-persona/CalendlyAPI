package com.example.CalendarlyFetch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "internal_user_temp")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class InternalUserTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long externalSystemId;
    private String externalUserId;
    private String email;
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String rawPayload;

    private LocalDateTime syncedAt;
}
