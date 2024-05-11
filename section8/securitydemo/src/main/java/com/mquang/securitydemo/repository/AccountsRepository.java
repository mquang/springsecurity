package com.mquang.securitydemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mquang.securitydemo.model.Accounts;

@Repository	
public interface AccountsRepository extends CrudRepository<Accounts, Long> {
	
	Accounts findByEmployeeId(int employeeId);
	
}
