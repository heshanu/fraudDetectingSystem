package com.example.dashboard_service.repository;

import com.example.dashboard_service.model.TransactionStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsRepository extends MongoRepository<TransactionStats, String> {

    // Status eka (FRAUD hari SUCCESS hari) anuwa counts ganna
    long countByStatus(String status);

    // User ID eka anuwa daththa ganna (Optional - analytics walata wadagath wei)
    List<TransactionStats> findByUserId(String userId);

    // Idira analytics walata amount eka anuwa filter karanna puluwan mehema
    List<TransactionStats> findByAmountGreaterThan(double amount);
}