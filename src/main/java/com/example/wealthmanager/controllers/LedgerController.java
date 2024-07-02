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

import com.example.wealthmanager.entity.Ledger;
import com.example.wealthmanager.service.LedgerService;

import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/assets")
public class LedgerController {

	@Autowired
	private LedgerService laService;

	@GetMapping
	public String viewItems(Model model, HttpSession session) {
		String email=(String)session.getAttribute("userEmail");
		List<Ledger> items=laService.viewItemsByEmail(email);
		model.addAttribute("items", items);
		return "ledger";
	}
	
	@PostMapping("/add")
	public String addItem(@ModelAttribute Ledger la, HttpSession session,Model model) {
		String email = (String) session.getAttribute("userEmail");
		String message=laService.addItem(email,la);
		model.addAttribute("message",message);
		return "redirect:/assets";
	}
	
	@PostMapping("/pay")
    public String payAmount(@RequestParam String itemName, HttpSession session,Model model) {
		String email=(String)session.getAttribute("userEmail");
		String message="";
    	List<Ledger>items=laService.viewItemsByEmail(email);
    	for(Ledger ledge:items) {
    		if(ledge.getLedgerName().equals(itemName))
    			message=laService.deleteItem(ledge);
    	}
    	model.addAttribute("error",message);
        return "redirect:/assets";   
    }
}