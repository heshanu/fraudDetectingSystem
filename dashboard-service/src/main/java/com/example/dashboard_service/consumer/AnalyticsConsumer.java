package com.example.dashboard_service.consumer;

import com.example.dashboard_service.model.Transaction;
import com.example.dashboard_service.model.TransactionStats;
import com.example.dashboard_service.repository.StatsRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticsConsumer {

    private final StatsRepository repository;

    public AnalyticsConsumer(StatsRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "transactions-topic", groupId = "analytics-group")
    public void consumeAll(Transaction txn) {
        saveStats(txn, "SUCCESS");
    }

    @KafkaListener(topics = "flagged-transactions-topic", groupId = "analytics-group")
    public void consumeFraud(Transaction txn) {
        saveStats(txn, "FRAUD");
    }

    private void saveStats(Transaction txn, String status) {
        TransactionStats stats = new TransactionStats();
        stats.setTransactionId(txn.getTransactionId());
        stats.setUserId(txn.getUserId());
        stats.setAmount(txn.getAmount());
        stats.setStatus(status);
        stats.setTimestamp(LocalDateTime.now());
        repository.save(stats);
    }
}
