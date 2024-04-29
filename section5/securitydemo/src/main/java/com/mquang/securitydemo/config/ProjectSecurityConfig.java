package com.mquang.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
		http.csrf(crsf -> crsf.disable()) //for testing with Postman/Thunder client
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccount", "/myBalance").authenticated()
				.requestMatchers("/notices", "/register").permitAll()
				.requestMatchers("/h2-console/**").permitAll() //remove if not using h2 database
				);
//		http.csrf(csrf -> csrf .ignoringRequestMatchers("/h2-console/**")); //remove if not using h2 database
//		http.headers().frameOptions().disable();
		http.headers(headers -> headers.frameOptions(frameOption -> frameOption.disable())); //remove if not using h2 database
		 
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}

	/*
	 * org.springframework.security.authentication.dao.DaoAuthenticationProvider(AbstractUserDetailsAuthencitationProvider)
	 * authenticate
	 * 	retrieveUser
	 * 		this.getUserDetailsService().loadUserByUsername
	 * 	additionalAuthenticationChecks
	 * 		this.passwordEncoder.matches(CharSequence rawPassword, String encodedPassword)
	 * 			Different implementations of PaswordEncoder inside Spring Security
	 * 				> NoOpPasswordEncoder (default)
	 * 				> StandardPasswordEncoder
	 * 				> Pbkdf2PasswordEncoder
	 * 				> BCryptPasswordEncoder
	 * 				> SCryptPasswordEncoder
	 * 				> Argon2PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder(); //ctrl + click into this constructor to see its default BCryptVersion, strength and secureRandom settings
	}

}
