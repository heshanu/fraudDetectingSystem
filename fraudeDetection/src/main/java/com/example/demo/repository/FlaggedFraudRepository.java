package com.example.demo.repository;

import com.example.demo.model.FlaggedTransactionModel;
import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlaggedFraudRepository extends JpaRepository<FlaggedTransactionModel, String> {
}
