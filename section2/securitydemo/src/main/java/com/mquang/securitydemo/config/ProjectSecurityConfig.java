package com.mquang.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain overrideDefaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.requestMatchers("/myAccount", "/myBalance").authenticated()
			.requestMatchers("/notices").permitAll()
			.and().formLogin()
			.and().httpBasic();
		return http.build();
	}
}
