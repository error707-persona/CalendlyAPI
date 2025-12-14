package com.example.CalendarlyFetch.repository;

import com.example.CalendarlyFetch.entity.ApiActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiActivityLogRepository extends JpaRepository<ApiActivityLog, Long> {

    List<ApiActivityLog> findByRequestId(String requestId);

    List<ApiActivityLog> findByExternalSystemIdOrderByCreatedAtDesc(Long externalSystemId);

    List<ApiActivityLog> findBySuccessFlagFalse();
}
