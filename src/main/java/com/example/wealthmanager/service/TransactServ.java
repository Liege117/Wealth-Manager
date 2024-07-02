package com.example.wealthmanager.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wealthmanager.entity.Transactions;
import com.example.wealthmanager.entity.User;
import com.example.wealthmanager.repo.TransactRep;

@Service
public class TransactServ {

    @Autowired
    private TransactRep transactionRepository;
    
    @Autowired
    private UserServ userService;

    public double getIncome(String email) {
    	List<Transactions> transactions = getAllTransactionsByEmail(email);
    	double totalIncome = transactions.stream().filter(t -> "Credit".equalsIgnoreCase(t.getType()))
				.mapToDouble(Transactions::getAmount).sum();
    	return totalIncome;
    }
    
    public double getExpense(String email) {
    	List<Transactions> transactions = getAllTransactionsByEmail(email);
    	double totalExpense = transactions.stream().filter(t -> "Debit".equalsIgnoreCase(t.getType()))
				.mapToDouble(Transactions::getAmount).sum();
    	return totalExpense;
    }

    public String addTransaction(String email, Transactions transaction) {
    	
    	if(checkBalance(email, transaction.getAmount())) {
    		transaction.setEmail(email);
    		transactionRepository.save(transaction);
    		userService.updateBalance(email, transaction.getAmount(),transaction.getType());
    		return "Transaction Successful";
    	}
        return "Insufficient Balance";
    }
    
    public boolean checkBalance(String email, double amt) {
    	User user = userService.findByEmail(email);
        double blnc = user.getSavings();
        
    	if(blnc<amt)
    		return false;
    	
    	return true;
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
    
    public List<Transactions> getAllTransactionsByEmail(String email) {
        return transactionRepository.findByEmail(email);
    }

	public List<Transactions> filterTransactions(String email, String filterType, String filterValue) {
		List<Transactions> transactions;
		LocalDate filterDate=LocalDate.parse(filterValue);
		System.out.println(filterDate.getClass()+" "+filterValue.getClass());
		switch (filterType) {
        	case "monthly":
        		LocalDate startOfMonth = filterDate.with(TemporalAdjusters.firstDayOfMonth());
                LocalDate endOfMonth = filterDate.with(TemporalAdjusters.lastDayOfMonth());
                transactions = transactionRepository.findByEmailAndDateBetween(email, startOfMonth, endOfMonth);
                break;
        	case "weekly":
        		LocalDate startOfWeek = filterDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate endOfWeek = filterDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                transactions = transactionRepository.findByEmailAndDateBetween(email, startOfWeek, endOfWeek);
                break;
        	case "date":
        		transactions = transactionRepository.findByEmailAndDate(email, filterDate);
                break;
        	default:
        		transactions=List.of();	
		}
		return transactions;
	}
}