package com.example.demo.consumer;

import com.example.demo.model.FlaggedTransactionModel;
import com.example.demo.repository.FlaggedFraudRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlaggedRecordConsumerTest {

    @Mock
    private FlaggedFraudRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    private FlaggedRecordConsumer consumer;

    @BeforeEach
    void setUp() {
        consumer = new FlaggedRecordConsumer(repository, objectMapper);
    }

    @Test
    void shouldSuccessfullyConsumeAndSaveRecord() throws Exception {
        // Given
        String jsonMessage = "{\"transactionId\": \"TXN123\"}";
        FlaggedTransactionModel mockModel = new FlaggedTransactionModel();
        mockModel.setTransactionId("TXN123");

        // ObjectMapper eken JSON eka object ekakata convert karana hati mock kireema
        when(objectMapper.readValue(eq(jsonMessage), eq(FlaggedTransactionModel.class)))
                .thenReturn(mockModel);

        // When
        consumer.consume(jsonMessage);

        // Then
        // Repository eke save method eka call unada balanna
        verify(repository, times(1)).save(mockModel);
        verify(objectMapper, times(1)).readValue(jsonMessage, FlaggedTransactionModel.class);
    }

    @Test
    void shouldHandleParsingException() throws Exception {
        // Given
        String invalidJson = "invalid-json";

        // ObjectMapper eka throw karana exception ekak mock kireema
        when(objectMapper.readValue(anyString(), eq(FlaggedTransactionModel.class)))
                .thenThrow(new RuntimeException("Parsing error"));

        // When
        consumer.consume(invalidJson);

        // Then
        // Exception ekak awoth repository save eka call wenna ba
        verify(repository, never()).save(any());
    }
}