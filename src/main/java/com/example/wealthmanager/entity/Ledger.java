package com.example.wealthmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ledgerName;
	private String email;
	private String ledgerType;
	private double coverageAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLedgerName() {
		return ledgerName;
	}
	
	public String getEmail() {
		return email;
	}

	public String getLedgerType() {
		return ledgerType;
	}

	public double getCoverageAmount() {
		return coverageAmount;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
	}

	public void setCoverageAmount(double coverageAmount) {
		this.coverageAmount = coverageAmount;
	}
}