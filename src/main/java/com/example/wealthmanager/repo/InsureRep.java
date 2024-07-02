package com.example.wealthmanager.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wealthmanager.entity.Insurance;

@Repository
public interface InsureRep extends JpaRepository<Insurance, Long> {
    List<Insurance> findByEmail(String email);

	Optional<Insurance> findByPolicyNumber(String policyNumber);
}