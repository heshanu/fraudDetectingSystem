package com.example.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Service
public class AllTransactions {
    // volatile is CRITICAL for cross-thread visibility
    private final List<String> allMessages = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "transactions-topic",
            groupId = "notification-group3")
    public void consume(String message) {
        try {
            // System.out.println("RECEIVED DATA: " + message.toString());
            this.allMessages.add(message.toString());
        } catch (Exception e) {
            System.err.println("Error in listener: " + e.getMessage());
        }
    }

    public List<String> getAllData() {
        return allMessages;
    }

    public void clearRecords() {
        allMessages.clear();
    }
}
