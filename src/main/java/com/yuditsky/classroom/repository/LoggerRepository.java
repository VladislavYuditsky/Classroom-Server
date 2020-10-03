package com.yuditsky.classroom.repository;

import com.yuditsky.classroom.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggerRepository extends JpaRepository<LoggerEntity, Long> {
    List<LoggerEntity> findByUsername(String username);
}
