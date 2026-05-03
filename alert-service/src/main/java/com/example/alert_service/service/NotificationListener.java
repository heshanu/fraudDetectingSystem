package com.example.alert_service.service; // Verify this matches your folder structure

import com.example.alert_service.model.Transaction;
import lombok.extern.slf4j.Slf4j; // Better than System.out
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationListener {

    @KafkaListener(
            topics = "flagged-transactions-topic",
            groupId = "notification-group"
    )
    public void handleFraudAlert(Transaction transaction) {
        // 1. The Deserializer handles the 'null' check usually,
        // but it's safe to keep for ErrorHandlingDeserializer cases.
        if (transaction == null) {
            log.error("Received a null or un-parseable transaction from Kafka");
            return;
        }

        try {
            log.info("🚨 FRAUD ALERT RECEIVED 🚨");
            log.info("Transaction ID: {}", transaction.getTransactionId());
            log.info("User: {} | Amount: {} | Status: {}",
                    transaction.getUserId(),
                    transaction.getAmount(),
                    transaction.getTimestamp());

            // Add your actual notification logic here (e.g., sending an email)
            sendNotification(transaction);

        } catch (Exception e) {
            log.error("Failed to process notification for transaction {}: {}",
                    transaction.getTransactionId(), e.getMessage());
        }
    }

    private void sendNotification(Transaction transaction) {
        // Placeholder for email/SMS logic
        log.info("Notification sent successfully to User: {}", transaction.getUserId());
    }
}