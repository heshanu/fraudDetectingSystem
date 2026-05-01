package com.example.dashboard_service.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String transactionId;
    private String userId;
    private double amount;
    private String currency;
    private String timestamp;
}