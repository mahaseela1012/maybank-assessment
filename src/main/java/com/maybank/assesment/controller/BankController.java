package com.maybank.assesment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.assesment.model.Bank;
import com.maybank.assesment.model.BankInfo;
import com.maybank.assesment.model.BankListResponse;
import com.maybank.assesment.service.BankService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BankController {

	@Autowired
	private BankService bankService;

	private static final Map<String, List<String>> stateCityMap = Map.of("Selangor",
			List.of("Shah Alam", "Petaling Jaya", "Subang Jaya"), "Kuala Lumpur",
			List.of("Bukit Bintang", "Cheras", "Setapak"), "Penang",
			List.of("George Town", "Butterworth", "Bayan Lepas"), "Johor",
			List.of("Johor Bahru", "Batu Pahat", "Kluang"), "Sabah", List.of("Kota Kinabalu", "Sandakan", "Tawau"),
			"Sarawak", List.of("Kuching", "Miri", "Sibu"));

	@GetMapping("/")
	public String viewHomePage(Model model) {
		try {
			return findPaginated(1, "bankName", "asc", model);
		} catch (Exception e) {
			log.error("Unexpected error occurred: {}", e.getMessage(), e);
		}
		return null;
	}

	@GetMapping("/showNewBankForm")
	public String showNewBankForm(Model model) {
		try {
			model.addAttribute("bank", new Bank());
			model.addAttribute("states", stateCityMap.keySet());
			model.addAttribute("stateCityMap", stateCityMap);
			return "new_bank";
		} catch (Exception e) {
			log.error("Unexpected error on showNewBankForm: {}", e.getMessage(), e);
		}
		return null;
	}


	@PostMapping("/saveBank")
	public String saveBank(@ModelAttribute("bank") Bank bank) {
		try {
			bankService.saveBank(bank);
			return "redirect:/";
		} catch (Exception e) {
			log.error("Unexpected error on saveBank: {}", e.getMessage(), e);
		}
		return null;

	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable("id") long id, Model model) {
		try {
			Bank bank = bankService.getBankById(id);
			model.addAttribute("bank", bank);
			model.addAttribute("states", stateCityMap.keySet());
			model.addAttribute("stateCityMap", stateCityMap);
			return "update_bank";
		} catch (Exception e) {
			log.error("Unexpected error on showFormForUpdate: {}", e.getMessage(), e);
		}
		return null;
	}

	// Delete bank
	@GetMapping("/deleteBank/{id}")
	public String deleteBank(@PathVariable("id") long id) {
		try {
			bankService.deleteBankById(id);
			return "redirect:/";
		} catch (Exception e) {
			log.error("Unexpected error on deleteBank: {}", e.getMessage(), e);
		}
		return null;
	}

	@GetMapping("/negaraBankList")
	String getAllBanks(Model model) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ResponseEntity<String> bankList = bankService.getPosts();
			BankListResponse wrapperResponse = objectMapper.readValue(bankList.getBody(), BankListResponse.class);

            List<BankInfo> bankListResponse = wrapperResponse.getData();
			//log.info("res:" + bankListResponse);
			
			model.addAttribute("bankList", bankListResponse);
		} catch (Exception e) {
			log.error("Unexpected error on getAllBanks: {}", e.getMessage(), e);
		}
		return "bank_list";

	}

	// Pagination
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 5;
		try {

			Page<Bank> page = bankService.findPaginated(pageNo, pageSize, sortField, sortDir);
			List<Bank> listBanks = page.getContent();

			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());

			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

			model.addAttribute("listBanks", listBanks);
		} catch (Exception e) {
			log.error("Unexpected error findPaginated: {}", e.getMessage(), e);
		}
		return "index";
	}
}
