package com.yuditsky.classroom.repository;

import com.yuditsky.classroom.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoggerRepository extends JpaRepository<LoggerEntity, Long>, JpaSpecificationExecutor<LoggerEntity> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT username FROM logs WHERE date > (:date)")
    List<String> findDistinctUsernameByDateTimeAfter(@Param("date") LocalDateTime date);
}