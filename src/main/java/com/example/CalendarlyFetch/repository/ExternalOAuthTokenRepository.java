package com.example.CalendarlyFetch.repository;

import com.example.CalendarlyFetch.entity.ExternalOAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalOAuthTokenRepository extends JpaRepository<ExternalOAuthToken, Long> {

    Optional<ExternalOAuthToken> findByExternalSystemId(Long externalSystemId);
}
