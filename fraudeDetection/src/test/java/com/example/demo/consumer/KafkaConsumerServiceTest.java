package com.example.demo.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaConsumerServiceTest {

    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        // Class eka manual initialize kireema (Unit Test nisa)
        kafkaConsumerService = new KafkaConsumerService();
    }

    @Test
    @DisplayName("Message eka consume kalama eka list ekata add wenna ona")
    void shouldAddMessageToListOnConsume() {
        // 1. Given
        String testMessage = "Test Transaction Message";

        // 2. When
        kafkaConsumerService.consume(testMessage);

        // 3. Then
        assertThat(kafkaConsumerService.getAllData())
                .hasSize(1)
                .containsExactly(testMessage);
    }

    @Test
    @DisplayName("clearRecords call kalama list eka empty wenna ona")
    void shouldClearAllMessages() {
        // Given
        kafkaConsumerService.consume("Msg 1");
        kafkaConsumerService.consume("Msg 2");

        // When
        kafkaConsumerService.clearRecords();

        // Then
        assertThat(kafkaConsumerService.getAllData()).isEmpty();
    }

    @Test
    @DisplayName("Multiple messages consume kalama okkoma list ekata add wenna ona")
    void shouldHandleMultipleMessages() {
        // When
        kafkaConsumerService.consume("First");
        kafkaConsumerService.consume("Second");

        // Then
        assertThat(kafkaConsumerService.getAllData())
                .hasSize(2)
                .contains("First", "Second");
    }
}