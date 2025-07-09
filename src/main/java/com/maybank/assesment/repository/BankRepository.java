package com.maybank.assesment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maybank.assesment.model.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>{
	

}
