package com.example.wealthmanager.service;

import com.example.wealthmanager.entity.Insurance;
import com.example.wealthmanager.entity.Transactions;
import com.example.wealthmanager.repo.InsureRep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {

    @Autowired
    private InsureRep insuranceRepo;
    
    @Autowired
    private TransactServ transactService;

    public void addInsurance(String email, Insurance insurance,double amt) {
    	insurance.setEmail(email);
    	insurance.setTotalPaid(amt);
        insuranceRepo.save(insurance);
    }
    
    public String payMonthly(Insurance insurance) {
    	String email = insurance.getEmail();
    	double amt=insurance.getMonthlyAmount();
    	double paid=insurance.getTotalPaid();
    	String message;
    	if(transactService.checkBalance(email,amt)) {
    		Transactions transaction = new Transactions();
    		transaction.setAmount(amt);
    		transaction.setSubject(insurance.getPolicyType() + " Insurance Premium Payment");
    		transaction.setType("Debit");
    		transaction.setDate(LocalDate.now());
    		message=transactService.addTransaction(email, transaction);
    		
    		addInsurance(email, insurance, paid+amt);
    	}
    	else
			message="Insufficient Balance";
    	
        return message;
    }

    public void deleteInsurance(String policyNumber) {
        Insurance insurance = insuranceRepo.findByPolicyNumber(policyNumber).orElse(null);
        if (insurance != null) {
            insuranceRepo.delete(insurance);
        } else {
            throw new RuntimeException("Policy not found");
        }
    }

    public List<Insurance> getInsuranceByUser(String email) {
        return insuranceRepo.findByEmail(email);
    }

	public Insurance getInsuranceByPolicyNumber(String policyNumber) {
		Optional<Insurance> ins=insuranceRepo.findByPolicyNumber(policyNumber);
		return ins.get();
	}
}