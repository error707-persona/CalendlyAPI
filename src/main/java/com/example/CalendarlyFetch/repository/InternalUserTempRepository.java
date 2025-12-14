package com.example.CalendarlyFetch.repository;

import com.example.CalendarlyFetch.entity.InternalUserTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalUserTempRepository extends JpaRepository<InternalUserTemp, Long> {

    Optional<InternalUserTemp> findByExternalSystemIdAndExternalUserId(Long externalSystemId, String externalUserId);

    List<InternalUserTemp> findByExternalSystemId(Long externalSystemId);

    void deleteByExternalSystemId(Long externalSystemId);
}
