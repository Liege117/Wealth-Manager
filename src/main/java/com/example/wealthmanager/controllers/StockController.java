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

import com.example.wealthmanager.entity.Stock;
import com.example.wealthmanager.service.StockService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;
    
    @GetMapping
    public String viewStocks(Model model, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        List<Stock> stocks = stockService.getStocksByUser(email);
        if (stocks.isEmpty())
            model.addAttribute("message", "No stocks available.");
        else
            model.addAttribute("stocks", stocks);
        
        double invest=stockService.getInvestment(email);
        double ret=stockService.getReturns(email);
        
        model.addAttribute("totalInvestment",invest);
        model.addAttribute("totalReturns",ret);
        return "stocks";
    }

    @PostMapping("/add")
    public String addStock(@ModelAttribute Stock stock, HttpSession session,Model model) {
        String email = (String) session.getAttribute("userEmail");
		String message=stockService.addStock(stock,email);
		model.addAttribute("error",message);
        return "redirect:/stocks";
    }

    @GetMapping("/fetch")
    public String fetchPrice(Model model, HttpSession session){
    	String email = (String) session.getAttribute("userEmail");
        List<Stock> stocks = stockService.getStocksByUser(email);
        
        if (stocks.isEmpty())
            model.addAttribute("messageF", "No stocks available.");
        else {
        	for(Stock stock:stocks) {
        		double marketPrice=stockService.getCurrentStockPrice(stock.getStockName());
        		stock.setMarketPrice(marketPrice);
        		stock.setAmount(marketPrice*stock.getQuantity());
        		stockService.updateStockPrice(stock);
        	}
        }
        return "redirect:/stocks";
    }

    @PostMapping("/pay")
    public String deleteStock(@RequestParam String stockName, HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        Stock stock=stockService.getStockByStockName(stockName,email);
        
        String message=stockService.deleteStock(email,stock);
        model.addAttribute("error",message);
        return "redirect:/stocks";
    }
}