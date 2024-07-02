package com.example.wealthmanager.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private String gender;
    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private Double savings;
    
    private String cardNumber;
    private String cardHolderName;
    private LocalDate expiryDate;
    
    public String getName() {
        return name;
    }
    
	public String getEmail() {
        return email;
    }
	
	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
        return phoneNumber;
    }

	public String getGender() {
		return gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
    }
	
	public Double getSavings() {
		return savings;
	}

	public void setName(String name) {
	        this.name = name;
	}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
		this.password = password;
	}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setGender(String gender) {
		this.gender = gender;
	}

    public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
    
    public void setSavings(Double savings) {
		this.savings = savings;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
}