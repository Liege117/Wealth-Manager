package com.example.wealthmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wealthmanager.entity.User;

@Repository
public interface UserRep extends JpaRepository<User, String> {
	User findByEmail(String email);
}	