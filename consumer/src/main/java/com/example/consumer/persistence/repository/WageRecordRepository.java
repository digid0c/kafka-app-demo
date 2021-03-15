package com.example.consumer.persistence.repository;

import com.example.consumer.persistence.entity.WageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WageRecordRepository extends JpaRepository<WageRecord, Long> {
}
