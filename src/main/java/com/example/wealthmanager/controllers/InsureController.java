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

import com.example.wealthmanager.entity.Insurance;
import com.example.wealthmanager.service.InsuranceService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/insurance")
public class InsureController {

    @Autowired
    private InsuranceService insuranceService;
    
    @GetMapping
    public String viewInsurance(Model model, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        List<Insurance> policies = insuranceService.getInsuranceByUser(email);
        model.addAttribute("policies", policies);
        return "insurance";
    }

    @PostMapping("/add")
    public String addInsurance(@ModelAttribute Insurance insurance, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        insuranceService.addInsurance(email,insurance,0);   
        return "redirect:/insurance";
    }
    
    @PostMapping("/pay")
    public String payInsuranceAmount(@RequestParam String policyNumber,Model model) {
    	Insurance insurance = insuranceService.getInsuranceByPolicyNumber(policyNumber);
    	String message=insuranceService.payMonthly(insurance);
    	model.addAttribute("error",message);
        return "redirect:/insurance";
    }

    @PostMapping("/delete")
    public String deleteInsurance(@RequestParam String policyNumber) {
        insuranceService.deleteInsurance(policyNumber);
        return "redirect:/insurance";
    }
}