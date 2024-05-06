package com.mquang.securitydemo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mquang.securitydemo.model.Employee;
import com.mquang.securitydemo.repository.EmployeeRepository;

/*
 * implementing a custom AuthenticationProvider
 * with this approach, we can get rid of implementing a custom UserDetailsService in section 4
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder; //inject the bean created from ProjectSecurityConfig

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		List<Employee> employee = employeeRepository.findByEmail(username);
		if(employee.size() > 0) {
			if(passwordEncoder.matches(pwd, employee.get(0).getPwd())) {
				List<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(employee.get(0).getRole()));
				return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
			} else {
				throw new BadCredentialsException("Invalid password");
			}
		} else {
			throw new BadCredentialsException("No user registered with this details");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
