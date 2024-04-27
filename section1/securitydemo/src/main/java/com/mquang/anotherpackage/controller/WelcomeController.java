package com.mquang.anotherpackage.controller; //this is not the same package with the Spring main class so @ComponentScan is required

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/another-package-welcome")
	public String sayWelcome() {
		return "Welcome to Spring Application";
	}
}
