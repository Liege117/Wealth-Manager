package com.example.wealthmanager.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wealthmanager.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByEmail(String email);
	Optional<Stock> getStockByStockNameAndEmail(String stockName,String email);
}