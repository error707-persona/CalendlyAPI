package com.example.CalendarlyFetch.repository;

import com.example.CalendarlyFetch.entity.ExternalFieldMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalFieldMappingRepository extends JpaRepository<ExternalFieldMapping, Long> {

    List<ExternalFieldMapping> findByExternalApiEndpointId(Long externalApiEndpointId);

    List<ExternalFieldMapping> findByExternalApiEndpointIdAndIsRequiredTrue(Long externalApiEndpointId);
}
