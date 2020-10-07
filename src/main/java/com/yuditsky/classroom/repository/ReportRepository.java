package com.yuditsky.classroom.repository;

import com.yuditsky.classroom.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    List<ReportEntity> findByReportingDate(LocalDate date);

    Optional<ReportEntity> findByRecipientUsername(String username);
}
