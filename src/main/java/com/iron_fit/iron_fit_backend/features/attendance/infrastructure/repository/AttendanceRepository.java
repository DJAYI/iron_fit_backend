package com.iron_fit.iron_fit_backend.features.attendance.infrastructure.repository;

import com.iron_fit.iron_fit_backend.features.attendance.domain.entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findAllByClient_Id (Long clientId);
}
