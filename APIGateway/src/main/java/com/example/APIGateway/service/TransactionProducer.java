package com.example.APIGateway.service;

import com.example.APIGateway.model.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {
    private static final String TOPIC = "transactions-topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(Transaction transaction) {
        kafkaTemplate.send(TOPIC, transaction.getTransactionId(), transaction);
    }
}
