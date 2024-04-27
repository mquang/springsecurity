package com.mquang.securitydemo.controller; //this is the same package with the Spring main class so @ComponentScan is optional

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/same-package-welcome")
	public String sayWelcome() {
		return "Welcome to Spring Application"; // this is what we will see when access
												// http://localhost:8080/same-package-welcome before
												// adding spring security dependencies in pom.xml

		// after adding spring security dependencies in pom.xml, when access this end
		// point, our application will redirect to http://localhost:8080/login first
		// with the default credential: username is 'user' and random password, printed
		// at INFO level when the application starts up

		// we can configure a custom username/password in application.properties using
		// the keys spring.security.user.name and spring.security.user.password

		//Spring Security internal flow:
		//org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration
		//	SecurityFilterChainConfiguration.defaultSecurityFilterChain
		
		//1. Spring Security Filters
		//org.springframework.security.web.access.intercept.AuthorizationFilter.doFilter
		//org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter.generateLoginPageHtml
		//org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.attemptAuthentication
		//2. AuthenticationManager
		//org.springframework.security.authentication.ProviderManager(AuthenticationManager).authenticate
		//3. AuthenticationProvider
		//org.springframework.security.authentication.dao.DaoAuthenticationProvider.authenticate
		//org.springframework.security.authentication.dao.DaoAuthenticationProvider.retrieveUser
		//4. UserDetailsManager/UserDetailsService
		//	getUserDetailsService()
		//		org.springframework.security.provisioning.InMemoryUserDetailsManager.loadUserByUsername
		//org.springframework.security.authentication.dao.DaoAuthenticationProvider.additionalAuthenticationChecks
	}
}
