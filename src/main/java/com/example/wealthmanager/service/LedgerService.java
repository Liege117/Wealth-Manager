package com.example.wealthmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wealthmanager.entity.Ledger;
import com.example.wealthmanager.entity.Transactions;
import com.example.wealthmanager.repo.LedgerRep;
@Service
public class LedgerService {

	@Autowired
	private LedgerRep ledgerep;
	
	@Autowired
	private TransactServ transactService;
	
	public List<Ledger> viewItemsByEmail(String email) {
		return ledgerep.findByEmail(email);
	}
	
	public String addItem(String email, Ledger ledger) {
		String message;       
        if(transactService.checkBalance(email, ledger.getCoverageAmount())) {
        	Transactions transaction=new Transactions();
        	transaction.setAmount(ledger.getCoverageAmount());
        	transaction.setSubject(ledger.getLedgerType()+" - "+ledger.getLedgerName());
        	transaction.setType("Debit");
        	transaction.setDate(LocalDate.now());
        	message=transactService.addTransaction(email,transaction);
        
        	ledger.setEmail(email);
        	ledgerep.save(ledger);
        }
        else
        	message="Insufficient Balance";
        
        return message;
    }

    public String deleteItem(Ledger la) {
       
        System.out.println(la.getLedgerName()+" "+la.getCoverageAmount());
        
        Transactions transaction=new Transactions();
        transaction.setAmount(la.getCoverageAmount());
        transaction.setSubject(la.getLedgerType()+" - "+la.getLedgerName());
        transaction.setType("Credit");
        transaction.setDate(LocalDate.now());
        String message=transactService.addTransaction(la.getEmail(),transaction);
        
        ledgerep.delete(la);
        return message;
    }
}