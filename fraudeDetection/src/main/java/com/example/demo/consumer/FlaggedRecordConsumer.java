package com.example.demo.consumer;
import com.example.demo.model.FlaggedTransactionModel;
import com.example.demo.repository.FlaggedFraudRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class FlaggedRecordConsumer {

    private final FlaggedFraudRepository repository;
    private final ObjectMapper objectMapper;

    public FlaggedRecordConsumer(FlaggedFraudRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(
            topics = "flagged-transactions-topic",
            groupId = "recovery-group4", // Use a COMPLETELY new name here
            properties = {"auto.offset.reset=earliest"} // Force read from start
    )
    public void consume(String message) {
        try {
            FlaggedTransactionModel record = objectMapper.readValue(message, FlaggedTransactionModel.class);
            repository.save(record);
            System.out.println("Recovered and Saved: " + record.getTransactionId());
        } catch (Exception e) {
            System.err.println("Failed to save existing record: " + e.getMessage());
        }
    }

}