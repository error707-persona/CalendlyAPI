package com.example.CalendarlyFetch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CalendarlyFetch.entity.ExternalSystem;

import jakarta.transaction.Status;

@Repository
public interface ExternalSystemRepository
        extends JpaRepository<ExternalSystem, Long> {

    Optional<ExternalSystem> findBySystemCode(String systemCode);

    Optional<ExternalSystem> findBySystemCodeAndStatus(
            String systemCode,
            Status status);

    List<ExternalSystem> findByStatus(Status status);
}
