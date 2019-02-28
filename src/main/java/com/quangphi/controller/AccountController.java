package com.quangphi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quangphi.exception.ExistsException;
import com.quangphi.model.AccountDTO;
import com.quangphi.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

	private static final String ACCOUNT_MANAGEMENT_PATH = "account/account-management";

	@Autowired
	private AccountService accountService;

	@GetMapping
	public String home(ModelMap model) {
		model.addAttribute("status", true);
		model.addAttribute("accountDTO", new AccountDTO());
		model.addAttribute("allAccounts", accountService.getAllAccounts());
		return AccountController.ACCOUNT_MANAGEMENT_PATH;
	}

	@GetMapping("/action")
	public String getHome() {
		return "redirect:/account";
	}

	@PostMapping("/action")
	public String addAccount(ModelMap model, @Valid AccountDTO accountDTO, BindingResult bindingResult,
			@RequestParam String action) {
		
		boolean isAdd = action.equals("Add");
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("status", isAdd);
			model.addAttribute("accountDTO", accountDTO);
			model.addAttribute("allAccounts", accountService.getAllAccounts());
			return AccountController.ACCOUNT_MANAGEMENT_PATH;
		}
		
		if(isAdd) {
			return addAccount(accountDTO, model);
		}
		return editAccount(accountDTO, model);
	}

	public String addAccount(AccountDTO accountDTO, ModelMap model) {
		try {
			accountService.addAccount(accountDTO);
			model.addAttribute("accountDTO", new AccountDTO());
			model.addAttribute("message", "Thêm tài khoản "+"\""+accountDTO.getUsername()+"\" thành công");
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
		model.addAttribute("success", true);
		model.addAttribute("message", "Tài khoản đã được sửa");
		return home(model);
	}

	@GetMapping("/delete/{username}")
	public String deleteAccount(@PathVariable String username) {
		accountService.delete(username);
		return "redirect:/account";
	}

	@GetMapping("/search")
	public String search(@RequestParam(required=false) String keyword, ModelMap model) {
		if (keyword == null || keyword.isEmpty()) {
			return "redirect:/account";
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