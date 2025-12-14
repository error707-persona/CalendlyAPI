package com.example.CalendarlyFetch.repository;


import com.example.CalendarlyFetch.entity.ExternalAuthConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalAuthConfigRepository extends JpaRepository<ExternalAuthConfig, Long> {

    Optional<ExternalAuthConfig> findByExternalSystemId(Long externalSystemId);

    Optional<ExternalAuthConfig> findByExternalSystem_SystemCode(String systemCode);
}

