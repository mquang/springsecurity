package com.mquang.securitydemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mquang.securitydemo.model.Employee;
import com.mquang.securitydemo.repository.EmployeeRepository;

@RestController
public class LogonController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder; //inject the bean created from ProjectSecurityConfig
	
	//for now we can test this end point with Postman/Thunder Client (VS Code)
	/*
	 * {
		  "email": "dungcv1@bidv.com.vn",
		  "pwd": "12345",
		  "role": "user"
		}
	 */
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Employee employee) {
		Employee savedEmployee = null;
		ResponseEntity response = null;
		try {
			String hashPwd = passwordEncoder.encode(employee.getPwd());
			employee.setPwd(hashPwd);
			savedEmployee = employeeRepository.save(employee);
			if(savedEmployee.getId() > 0) {
				response = ResponseEntity
						.status(HttpStatus.CREATED)
						.body("Given user details are successfully registered");
			}
		} catch(Exception ex) {
			response = ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occured due to " + ex.getMessage());
		}
		return response;
	}
	
	@RequestMapping("/user")
    public Employee getUserDetailsAfterLogin(Authentication authentication) {
        List<Employee> employees = employeeRepository.findByEmail(authentication.getName());
        if (employees.size() > 0) {
            return employees.get(0);
        } else {
            return null;
        }

    }
}
