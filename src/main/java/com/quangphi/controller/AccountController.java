package com.quangphi.controller;

import javax.validation.Valid;

import com.quangphi.exception.ExistsException;
import com.quangphi.model.AccountDTO;
import com.quangphi.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {

	private static final String ACCOUNT_MANAGEMENT_PATH = "account/account-management";

	@Autowired
	private AccountService accountService;

	@GetMapping("/home")
	public String home(ModelMap model) {
		model.addAttribute("status", true);
		model.addAttribute("accountDTO", new AccountDTO());
		model.addAttribute("allAccounts", accountService.getAllAccounts());
		return AccountController.ACCOUNT_MANAGEMENT_PATH;
	}

	@PostMapping("/action")
	public String addAccount(ModelMap model, @Valid AccountDTO accountDTO, BindingResult bindingResult,
			@RequestParam String action) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("status", action.equals("Add"));
			model.addAttribute("accountDTO", accountDTO);
			model.addAttribute("allAccounts", accountService.getAllAccounts());
			return AccountController.ACCOUNT_MANAGEMENT_PATH;
		}
		if(action.equals("Add")) {
			return addAccount(accountDTO, model);
		}else {
			return editAccount(accountDTO, model);
		}
	}

	public String addAccount(AccountDTO accountDTO, ModelMap model) {
		try {
			accountService.addAccount(accountDTO);
			model.addAttribute("accountDTO", new AccountDTO());
		} catch (ExistsException e) {
			model.addAttribute("username_error", "Tên đăng nhập đã tồn tại !");
			model.addAttribute("accountDTO", accountDTO);
		}
		model.addAttribute("allAccounts", accountService.getAllAccounts());
		model.addAttribute("status", true);
		return AccountController.ACCOUNT_MANAGEMENT_PATH;
	}

	public String editAccount(AccountDTO accountDTO, ModelMap model) {
		accountService.updateAccount(accountDTO);
		return "redirect:/account/home";
	}

	@GetMapping("/delete/{username}")
	public String deleteAccount(@PathVariable String username) {
		accountService.delete(username);
		return "redirect:/account/home";
	}

	@GetMapping("/search")
	public String search(@RequestParam String keyword, ModelMap model) {
		if (keyword.isEmpty()) {
			return "redirect:/account/home";
		}
		model.addAttribute("allAccounts", accountService.getByUsernameOrFullnameContaining(keyword));
		model.addAttribute("accountDTO", new AccountDTO());
		model.addAttribute("status", true);
		model.addAttribute("keyword", keyword);
		return AccountController.ACCOUNT_MANAGEMENT_PATH;
	}

	@GetMapping("/edit/{username}")
	public String edit(ModelMap model, @PathVariable String username) {
		model.addAttribute("accountDTO", accountService.get(username));
		model.addAttribute("allAccounts", accountService.getAllAccounts());
		model.addAttribute("status", false);
		return AccountController.ACCOUNT_MANAGEMENT_PATH;
	}

}