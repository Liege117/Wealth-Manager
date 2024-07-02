package com.example.wealthmanager.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wealthmanager.entity.Ledger;

import jakarta.transaction.Transactional;

@Repository
public interface LedgerRep extends JpaRepository<Ledger, Long> {
    List<Ledger> findByEmail(String email);

    //@Transactional
    //@Query("Select * from Ledger l where l.item_name=:itemName and l.email=:email")
	//Optional<Ledger> getLedgerByEmailAndLedgerName(String email, String ledgerName);
}