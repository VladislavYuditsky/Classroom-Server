package com.yuditsky.classroom.repository;

import com.yuditsky.classroom.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerRepository extends JpaRepository<LoggerEntity, Long>, JpaSpecificationExecutor<LoggerEntity> {
}
