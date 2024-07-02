package com.example.wealthmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wealthmanager.entity.Transactions;
import com.example.wealthmanager.entity.User;
import com.example.wealthmanager.service.TransactServ;
import com.example.wealthmanager.service.UserServ;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("transactions")
public class TransactControl {

	@Autowired
	private TransactServ transactionService;

	@Autowired
	private UserServ userService;
	
	@GetMapping
	public String showTransactions(Model model, HttpSession session) {	
		String email = (String) session.getAttribute("userEmail");
		List<Transactions> transactions = transactionService.getAllTransactionsByEmail(email);
		User user = userService.findByEmail(email);

		double totalIncome=transactionService.getIncome(email);
		double totalExpense=transactionService.getExpense(email);
		
		model.addAttribute("transactions", transactions);
		model.addAttribute("user", user);
		
		model.addAttribute("totalIncome", totalIncome);
		model.addAttribute("totalExpense", totalExpense);
		
		return "transactionsDetails";
	}
	
	@PostMapping("/add")
	public String addTransaction(@ModelAttribute Transactions transaction, HttpSession session, Model model) { 
		String email = (String) session.getAttribute("userEmail");
		String message=transactionService.addTransaction(email, transaction);
		model.addAttribute("error",message);
		return "redirect:/transactions";
	}

	@GetMapping("/filter")
	public String filterTransactions(@RequestParam("filterType") String filterType,
									@RequestParam("filterValue") String filterValue,
									Model model, HttpSession session) {
		String email = (String) session.getAttribute("userEmail");
		List<Transactions> filterTrans = transactionService.filterTransactions(email, filterType, filterValue);
		model.addAttribute("transactions", filterTrans);
		return "transactionsDetails";
	}
}