package com.example.demo.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AllTransactionsUnitTest {

    private AllTransactions allTransactions;

    @BeforeEach
    void setUp() {
        allTransactions = new AllTransactions();
    }

    @Test
    @DisplayName("Should add message to list when consume is called")
    void shouldAddMessageToList() {
        // 1. Given (Test data setup)
        String testMessage = "{\"id\": 1, \"amount\": 500.0}";

        // 2. When (Method eka manually call kireema)
        allTransactions.consume(testMessage);

        // 3. Then (Result eka check kireema)
        assertThat(allTransactions.getAllData())
                .hasSize(1)
                .containsExactly(testMessage);
    }

    @Test
    @DisplayName("Should clear records correctly")
    void shouldClearRecords() {
        // Given
        allTransactions.consume("Message 1");
        allTransactions.consume("Message 2");

        // When
        allTransactions.clearRecords();

        // Then
        assertThat(allTransactions.getAllData()).isEmpty();
    }

    @Test
    @DisplayName("Should handle null messages gracefully")
    void shouldHandleNullMessages() {
        // When - Null message ekak dhenawa
        allTransactions.consume(null);
        assertThat(allTransactions.getAllData()).isEmpty();
    }
}