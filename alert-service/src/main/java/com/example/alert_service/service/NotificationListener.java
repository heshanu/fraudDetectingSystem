package com.example.alert_service.service;

import com.example.alert_service.model.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {
    @KafkaListener(topics = "flagged-transactions-topic", groupId = "notification-group")
    public void handleFraudAlert(Transaction transaction) {
        try {
            if (transaction == null) {
                System.out.println("Received null transaction!");
                return;
            }

            System.out.println("Processing notification for: " + transaction.getTransactionId());

            // Log details to verify
            System.out.println("User: " + transaction.getUserId() + " | Amount: " + transaction.getAmount());

        } catch (Exception e) {
            System.err.println("Error in notification logic: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
