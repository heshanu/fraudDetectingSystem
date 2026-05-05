package com.example.demo.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.model.Transaction;
import com.example.demo.repository.FraudRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MySQLWriteConsumerTest {

    @Mock
    private FraudRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    private MySQLWriteConsumer consumer;

    @BeforeEach
    void setUp() {
        // Dependency injection manual kireema
        consumer = new MySQLWriteConsumer(repository, objectMapper);
    }

    @Test
    @DisplayName("Valid JSON ekak consume kalama eka DB ekata save wenna ona")
    void shouldSuccessfullyConsumeAndSave() throws Exception {
        // 1. Given
        String jsonMessage = "{\"transactionId\": \"TXN_999\", \"amount\": 1500.0}";
        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransactionId("TXN_999");

        // Object mapper eke behavior eka mock kireema
        when(objectMapper.readValue(eq(jsonMessage), eq(Transaction.class)))
                .thenReturn(mockTransaction);

        // 2. When
        consumer.consume(jsonMessage);

        // 3. Then
        // Repository eke save method eka adala object eka ekka call unada balanna
        verify(repository, times(1)).save(mockTransaction);
        verify(objectMapper, times(1)).readValue(jsonMessage, Transaction.class);
    }

    @Test
    @DisplayName("JSON parsing fail unoth repository save eka call wenna ba")
    void shouldNotSaveWhenParsingFails() throws Exception {
        // 1. Given
        String invalidJson = "invalid-json";

        when(objectMapper.readValue(anyString(), eq(Transaction.class)))
                .thenThrow(new RuntimeException("Parsing Error"));

        // 2. When
        consumer.consume(invalidJson);

        // 3. Then
        // Parsing fail nisa save call eka kawadawath wenna ba
        verify(repository, never()).save(any());
    }
}