package com.mquang.securitydemo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
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
				.requestMatchers("/h2-console/**").permitAll() //remove if not using h2 database
				);
		http.csrf(csrf -> csrf .ignoringRequestMatchers("/h2-console/**")); //remove if not using h2 database
//		http.headers().frameOptions().disable();
		http.headers(headers -> headers.frameOptions(frameOption -> frameOption.disable())); //remove if not using h2 database
		 
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	/*
	 * UserDetailsService
	 * 		loadUserByUsername(String username)
	 * 			> UserDetailsManager
	 * 					createUser(UserDetails user)
	 * 					updateUser(UserDetails user)
	 * 					deleteUser(String username)
	 * 					changePassword(String oldPwd, String newPwd)
	 * 					userExists(String username)
	 * 						> org.springframework.security.provisioning.InMemoryUserDetailsManager
	 * 						> org.springframework.security.provisioning.JdbcUserDetailsManager
	 * 						> LdapUserDetailsManager (requires spring-ldap-core and spring-security-ldap dependencies)
	 */
	
	/*
	 * UserDetails
	 * 		getAuthorities()
	 * 		getPassword()
	 * 		getUser()
	 * 			> User
	 */
	
	//Configuring users using InMemoryUserDetailsManager (for non-production environment) - approach 1 start
	/*
	@Bean
	public InMemoryUserDetailsManager buildInMemoryUsers() {
		UserDetails admin = User.withDefaultPasswordEncoder()
				.username("quangnm2")
				.password("12345")
				.authorities("admin")
				.build();
		UserDetails user1 = User.withDefaultPasswordEncoder()
				.username("thaopm")
				.password("12345")
				.authorities("read")
				.build();
		UserDetails user2 = User.withDefaultPasswordEncoder()
				.username("anhdtn")
				.password("12345")
				.authorities("read")
				.build();
		return new InMemoryUserDetailsManager(admin, user1, user2);
	}
	*/
	//Configuring users using InMemoryUserDetailsManager - approach 1 end
	
	//Configuring users using InMemoryUserDetailsManager (for non-production environment) - approach 2 start
	/*
	@Bean
	public InMemoryUserDetailsManager buildInMemoryUsers() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		UserDetails admin = User.withUsername("quangnm2")
				.password("12345")
				.authorities("admin")
				.build();
		UserDetails user1 = User.withUsername("thaopm")
				.password("12345")
				.authorities("read")
				.build();
		UserDetails user2 = User.withUsername("anhdtn")
				.password("12345")
				.authorities("read")
				.build();
		inMemoryUserDetailsManager.createUser(admin);
		inMemoryUserDetailsManager.createUser(user1);
		inMemoryUserDetailsManager.createUser(user2);
		return inMemoryUserDetailsManager;
	}
	*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	//Configuring users using InMemoryUserDetailsManager - approach 2 end
	
	/*
	@Bean
	public UserDetailsService userDetailsService(DataSource datasource) {
		return new JdbcUserDetailsManager(datasource);
	}
	*/
}
