package com.example.wealthmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wealthmanager.entity.User;
import com.example.wealthmanager.service.UserServ;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserCont {

	@Autowired
	private UserServ userService;

	@PostMapping("/signup")
	public String signupSubmit(@ModelAttribute User user, Model model) {
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("error", "Email already exists");
			return "signup";
		}
		user.setPassword(user.getPassword());
		userService.saveUser(user);
		return "login";
	}

	@PostMapping("/login")
	public String loginSubmit(@RequestParam String email, @RequestParam String password, HttpServletRequest request, Model model) {
		User user = userService.findByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("userEmail", email);
			return "redirect:/dashboard";
		}
		model.addAttribute("error", "Invalid email or password");
		return "login";
	}
}