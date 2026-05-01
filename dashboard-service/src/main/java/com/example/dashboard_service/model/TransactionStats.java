package com.example.dashboard_service.model;

import lombok.Data;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "transaction_stats")
public class TransactionStats {
    @Id
    private String id;
    private String transactionId;
    private String userId;
    private double amount;
    private String status; // SUCCESS hari FRAUD
    private LocalDateTime timestamp;
}