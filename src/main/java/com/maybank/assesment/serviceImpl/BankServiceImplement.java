package com.maybank.assesment.serviceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.maybank.assesment.model.Bank;
import com.maybank.assesment.repository.BankRepository;
import com.maybank.assesment.service.BankService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankServiceImplement implements BankService {

	@Autowired
	private BankRepository bankRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Bank> getAllBank() {
		try {
			List<Bank> listBank = bankRepository.findAll();
			return listBank;
		} catch (Exception e) {
			log.error("Unexpected error on getAllBank: {}", e.getMessage(), e);
		}
		return null;
	}

	@Override
	@Transactional
	public void saveBank(Bank b) {
		try {
			this.bankRepository.save(b);
		} catch (Exception e) {
			log.error("Unexpected error on saveBank: {}", e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Bank getBankById(long id) {
		try {
			Optional<Bank> optional = bankRepository.findById(id);
			Bank bank = null;
			if (optional.isPresent()) {
				bank = optional.get();
			} else {
				throw new RuntimeException(" Employee not found for id :: " + id);
			}
			return bank;
		} catch (Exception e) {
			log.error("Unexpected error on getBankById: {}", e.getMessage(), e);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteBankById(long id) {
		try {
			this.bankRepository.deleteById(id);
		} catch (Exception e) {
			log.error("Unexpected error at deleteBankById: {}", e.getMessage(), e);
		}
	}

	@Override
	public Page<Bank> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		try {
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
					: Sort.by(sortField).descending();

			Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
			return this.bankRepository.findAll(pageable);
		} catch (Exception e) {
			log.error("Unexpected error at findPaginated: {}", e.getMessage(), e);
		}
		return null;
	}

	public ResponseEntity<String> getPosts() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/vnd.BNM.API.v1+json");
			HttpEntity<String> entity = new HttpEntity<>(headers);

			String url = "https://api.bnm.gov.my/public/msb/5.1";

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			log.info("res:" + response);
			return response;
		} catch (HttpClientErrorException e) {
			log.error("Unexpected error at getPosts: {}", e.getMessage(), e);
		}
		return null;
	}
}
