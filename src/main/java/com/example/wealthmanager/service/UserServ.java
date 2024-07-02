package com.example.wealthmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.wealthmanager.entity.User;
import com.example.wealthmanager.repo.UserRep;

@Service
public class UserServ implements UserDetailsService{

    @Autowired
    private UserRep userRepository;

    public User saveUser(User user) {
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public void updateBalance(String email, double amount,String type) {
    	User user=(User) userRepository.findByEmail(email);
    	double balance=user.getSavings();
    	
    	if(type.equalsIgnoreCase("CREDIT"))
    		balance=balance+amount;
    	
    	else if(type.equalsIgnoreCase("DEBIT"))
			balance=balance-amount;
    	
    	user.setSavings(balance);
    	userRepository.save(user);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }
}