package com.example.demo.controller;

import com.example.demo.consumer.AllTransactions;
import com.example.demo.consumer.KafkaConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/fraud-alerts")
public class KafkaProducerController {
    private final KafkaConsumerService kafkaConsumerService;
    private final AllTransactions allTransactions;

    public KafkaProducerController(KafkaConsumerService kafkaConsumerService, AllTransactions allTransactions) {
        this.kafkaConsumerService = kafkaConsumerService;

        this.allTransactions = allTransactions;
    }

    // Returns a JSON array of all alerts: ["alert1", "alert2", ...]
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllAlerts() {
        return ResponseEntity.ok(kafkaConsumerService.getAllData());
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<String>> getAllTransactions() {
        return ResponseEntity.ok(allTransactions.getAllData());
    }


}
