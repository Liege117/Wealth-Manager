package com.example.wealthmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import com.example.wealthmanager.entity.User;
import com.example.wealthmanager.service.UserServ;

@Configuration
public class SecurityConfig {

	@Bean
    UserDetailsService userDetailsService(UserServ userService) {
		return email -> {
            User user = userService.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
        };
    }
	
    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {    
        http.authorizeRequests(requests -> requests.requestMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().permitAll());
                
    	http.csrf().disable();
    	return http.build();
    }
}