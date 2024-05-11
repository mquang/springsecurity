package com.mquang.securitydemo.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.mquang.securitydemo.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain overrideDefaultSecurityFilterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        
		http
		.securityContext((context) -> context
                .requireExplicitSave(false)) //I'm not going to take the responsibility of saving the authentication details inside the SecurityContextHolder. I'm letting the framework to do all the magic for me
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        /*
         * Previously, whenever we are trying to access the APIs through the browser,
		   we used to directly access these backend REST APIs through browser 
		   and there used to be a login page which is built up by the Spring Security framework
		   and we enter our credentials and behind the scenes it is going to create a JSESSIONID,
           and using the same JSESSIONID, we try to access all the subsequent requests
           without entering credentials.
           
           But now onwards, since we are going to use a separate UI application to log in and access 
           all the REST APIs, we need to let Spring Security framework, that, please create the JSESSIONID
           by following this sessionManagement that I have created here.
		   So with this configurations we are telling to the Spring Security framework, please always 
		   create the JSESSIONID after the initial login is completed. And the same JSESSIONID it is 
		   going to send to the UI application and my UI application can leverage the same for all the 
		   subsequent requests that it is going to make after the initial login.

		   Without these two lines, you have to share the credentials every time you are trying to access 
		   the secured API from your Angular application.
         */
		.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() { //solution 2 to handle CORS (instead of mentioning @CrossOrigin annotation on all the controllers inside our web app, we can define CORS related configurations globally like below)
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
            }
        }))
		//By default Spring Security blocks all http POST, PUT, DELETE, PATCH operations with an error of 403 if there is no CSRF solution implemented inside a web application
		//see org.springframework.security.web.csrf.CsrfFilter.doFilterInternal
		//UI: see login.component.ts, app.request.interceptors.ts, header.component.html, dashboard.service.ts
		.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/h2-console/**", "/contact", "/register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) //tell Spring Security to execute CsrfCookieFilter after BasicAuthenticationFilter (after logon)
		.authorizeHttpRequests((requests) -> requests
			/*
			//configuring authorities: hasAuthority(), hasAnyAuthority(), access() 
			.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT1") //try to access http://localhost:8080/myAccount?id=1 and sign in with quangnm2@bidv.com.vn/12345 and see the result
            .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
            .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
            .requestMatchers("/myCards").hasAuthority("VIEWCARDS") */
			//configuring role: hasRole(), hasAnyRole(), access() 	
			.requestMatchers("/myAccount").hasRole("USER")
            .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
            .requestMatchers("/myLoans").hasRole("USER")
            .requestMatchers("/myCards").hasRole("USER")
			.requestMatchers("/user").authenticated()
			.requestMatchers("/notices", "/contact", "/register").permitAll()
			.requestMatchers("/h2-console/**").permitAll() //remove if not using h2 database
			);

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
