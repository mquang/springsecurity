package com.mquang.securitydemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mquang.securitydemo.model.AccountTransactions;
import com.mquang.securitydemo.repository.AccountTransactionsRepository;

@RestController
public class BalanceController {

	 @Autowired
	    private AccountTransactionsRepository accountTransactionsRepository;

	    @GetMapping("/myBalance")
	    public List<AccountTransactions> getBalanceDetails(@RequestParam int id) {
	        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
	                findByEmployeeIdOrderByTransactionDtDesc(id);
	        if (accountTransactions != null ) {
	            return accountTransactions;
	        } else {
	            return null;
	        }
	    }
}
