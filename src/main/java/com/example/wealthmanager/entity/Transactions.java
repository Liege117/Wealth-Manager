package com.example.wealthmanager.entity;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transactions {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;	

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String type; // "credited" or "debited"

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public Double getAmount() {
		return amount;
	}

	public String getType() {
		return type;
	}

	public String getSubject() {
		return subject;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}