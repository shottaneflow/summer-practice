package com.practice.backend.repository;

import org.springframework.data.repository.CrudRepository;
import com.practice.backend.entity.BankAccount;


public interface BankAccountRepository extends CrudRepository<BankAccount,Long> {

	
}
