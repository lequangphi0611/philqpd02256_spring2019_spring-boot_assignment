package com.quangphi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.quangphi.model.AccountDTO;
import com.quangphi.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/login")
    public String loginPage(ModelMap model) {
        AccountDTO account = new AccountDTO();
        account.setFullname("null");
        model.addAttribute("account", account);
        return "account/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("account") @Valid AccountDTO account, BindingResult bindingResult,
            ModelMap model,  HttpSession session) {
        model.addAttribute("account", account);
        if (bindingResult.hasErrors()) {
            return "account/login";
        }
        if (!accountService.existsByIDAccount(account.getUsername())) {
            model.addAttribute("username_error", "Tên đăng nhập không tồn tại !");
            return "account/login";
        }
        AccountDTO accounts = accountService.get(account.getUsername());
        if (!account.getPassword().equals(accounts.getPassword())) {
            model.addAttribute("pass_error", "Mật khẩu không đúng !");
            return "account/login";
        }
        session.setAttribute("account", accounts);
        return "redirect:/index.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("account", null);
        return "redirect:/index.html";
    }

}