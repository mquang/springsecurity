package com.mquang.securitydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mquang.securitydemo.model.Accounts;
import com.mquang.securitydemo.repository.AccountsRepository;

@RestController
public class AccountController {
	
	@Autowired
    private AccountsRepository accountsRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam int id) {
        Accounts accounts = accountsRepository.findByEmployeeId(id);
        if (accounts != null ) {
            return accounts;
        }else {
            return null;
        }
    }
}
