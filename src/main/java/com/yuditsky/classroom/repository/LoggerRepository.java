package com.yuditsky.classroom.repository;

import com.yuditsky.classroom.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoggerRepository extends JpaRepository<LoggerEntity, Long> {
    Optional<LoggerEntity> findByUsername(String username);
}
