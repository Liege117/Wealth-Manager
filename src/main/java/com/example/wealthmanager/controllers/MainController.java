package com.example.wealthmanager.controllers;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.wealthmanager.entity.User;
import com.example.wealthmanager.service.ChartService;
import com.example.wealthmanager.service.StockService;
import com.example.wealthmanager.service.TransactServ;
import com.example.wealthmanager.service.UserServ;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
    private TransactServ transactionService;

    @Autowired
    private StockService stockService;
    
    @Autowired
    private UserServ userService;
    
    @Autowired
    private ChartService chartService;
    
    
	@GetMapping("/signup")
	public String signupForm(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@GetMapping("/")
	public String loginForm() {
		return "login";
	}
    
    @GetMapping("/dashboard")
	public String dashboard(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String email=(String) session.getAttribute("userEmail");
			User user = userService.findByEmail(email);
			
			double totalIncome = transactionService.getIncome(email);
	        double totalExpense = transactionService.getExpense(email);
	        
	        double totalInvestment = stockService.getInvestment(email);
	        double totalReturns = stockService.getReturns(email);
	        
	        double savings=user.getSavings();
	        try {
	            byte[] chartImage = chartService.savePieChart(totalInvestment, totalExpense, totalIncome,totalReturns);
	            String base64Image = Base64.getEncoder().encodeToString(chartImage);
	            model.addAttribute("chartImage", base64Image);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        model.addAttribute("savings", savings);
	        model.addAttribute("totalIncome", totalIncome);
			model.addAttribute("totalExpense", totalExpense);
			model.addAttribute("totalInvestment", totalInvestment);
			model.addAttribute("totalReturns", totalReturns);
			model.addAttribute("user", user);
			return "dashboard";
		}
		return "redirect:/login";
	}
}