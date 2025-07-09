package com.maybank.assesment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.maybank.assesment.model.Bank;

public interface BankService {
	public List<Bank> getAllBank();
	void saveBank(Bank b);
	Bank getBankById(long id);
	void deleteBankById(long id);
	Page<Bank> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	ResponseEntity<String> getPosts();
}
