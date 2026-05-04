package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "flagged_fraud_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlaggedTransactionModel {

    @Id
    @Column(name = "transaction_id", length = 100)
    private String transactionId; // Matches the String ID from Kafka

    private String userId;
    private double amount;
    private String currency;

    @Column(name = "created_at")
    private String timestamp;
}