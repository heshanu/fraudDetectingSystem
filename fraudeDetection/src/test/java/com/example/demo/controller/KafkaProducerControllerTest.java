package com.example.demo.controller;

import com.example.demo.consumer.AllTransactions;
import com.example.demo.consumer.KafkaConsumerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KafkaProducerController.class)
class KafkaProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KafkaConsumerService kafkaConsumerService;

    @MockitoBean
    private AllTransactions allTransactions;

    @Test
    @DisplayName("GET /all endpoint eka call kalama alerts list eka enna ona")
    void shouldReturnAllAlerts() throws Exception {
        // Given
        List<String> mockAlerts = Arrays.asList(
                "{\"transactionId\": \"T1014\", \"userId\": \"USER123\", \"amount\": 15000.0}",
                "{\"transactionId\": \"T1015\", \"userId\": \"USER456\", \"amount\": 2500.0}"
        );

        when(kafkaConsumerService.getAllData()).thenReturn(mockAlerts);

        // When & Then
        mockMvc.perform(get("api/v1/fraud-alerts/all"))
                .andExpect(status().isOk()) // HTTP 200 check kireema
                .andExpect(jsonPath("$.size()").value(2)) ;// List size eka check kireema

    }

    @Test
    @DisplayName("GET /transactions endpoint eka call kalama transaction list eka enna ona")
    void shouldReturnAllTransactions() throws Exception {
        // Given
        List<String> mockTransactions = Arrays.asList("TXN_001", "TXN_002");
        when(allTransactions.getAllData()).thenReturn(mockTransactions);

        // When & Then
        mockMvc.perform(get("/api/v1/fraud-alerts/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0]").value("TXN_001"));
    }

    @Test
    @DisplayName("Data nathnam empty list ekak enna ona")
    void shouldReturnEmptyListWhenNoData() throws Exception {
        // Given
        when(kafkaConsumerService.getAllData()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/v1/fraud-alerts/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }
}