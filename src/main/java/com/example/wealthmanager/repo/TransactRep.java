package com.example.wealthmanager.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wealthmanager.entity.Transactions;

@Repository
public interface TransactRep extends JpaRepository<Transactions, Long> {
    List<Transactions> findByEmail(String email);
    List<Transactions> findByEmailAndDate(String email, LocalDate date);

	List<Transactions> findByEmailAndDateBetween(String email, LocalDate startDate, LocalDate endDate);
}