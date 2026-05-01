package com.example.dashboard_service.controller;

import com.example.dashboard_service.repository.StatsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/dashboard")
public class AnalyticsController {
    private final StatsRepository repository;

    public AnalyticsController(StatsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/summary")
    public Map<String, Object> getDetailedSummary() {
        long total = repository.count();
        long fraud = repository.countByStatus("FRAUD");
        double fraudRate = (total > 0) ? (double) fraud / total * 100 : 0;

        return Map.of(
                "totalTransactions", total,
                "fraudDetected", fraud,
                "successTransactions", repository.countByStatus("SUCCESS"),
                "fraudRate", String.format("%.2f%%", fraudRate)
        );
    }
}
