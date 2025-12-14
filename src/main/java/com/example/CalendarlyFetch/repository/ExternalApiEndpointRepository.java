package com.example.CalendarlyFetch.repository;


import com.example.CalendarlyFetch.entity.ExternalApiEndpoint;
import com.example.CalendarlyFetch.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalApiEndpointRepository extends JpaRepository<ExternalApiEndpoint, Long> {

    Optional<ExternalApiEndpoint> findByExternalSystemIdAndEndpointCode(Long externalSystemId, String endpointCode);

    Optional<ExternalApiEndpoint> findByExternalSystem_SystemCodeAndEndpointCode(String systemCode, String endpointCode);

    List<ExternalApiEndpoint> findByExternalSystemIdAndStatus(Long externalSystemId, Status status);
}
