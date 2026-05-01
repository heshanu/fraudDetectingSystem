package com.example.APIGateway.controller;

import com.example.APIGateway.model.Transaction;
import com.example.APIGateway.service.TransactionProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionProducer producer;

    public TransactionController(TransactionProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/process")
    public Mono<String> processTransaction(@RequestBody Transaction transaction) {
        // WhatsApp Image 2026-05-01 at 00.01.03.jpg රූපසටහනේ පියවර 1 සහ 2 මෙහි සිදුවේ
        producer.sendTransaction(transaction);
        return Mono.just("Transaction sent to analyze!");
    }
}