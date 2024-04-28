package com.mquang.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain overrideDefaultSecurityFilterChain(HttpSecurity http) throws Exception {
		/*//Some methods are deprecated and planned to be removed in Spring Security 7
		http.authorizeHttpRequests()
			.requestMatchers("/myAccount", "/myBalance").authenticated()
			.requestMatchers("/notices").permitAll()
			.and().formLogin()
			.and().httpBasic();
		*/
		
		//Lambda DSL style
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccount", "/myBalance").authenticated()
				.requestMatchers("/notices").permitAll()
				);
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
}
