package com.quangphi.controller;

import com.quangphi.model.AccountDTO;
import com.quangphi.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value="/login")
    public String loginPage(ModelMap model) {
        model.addAttribute("account", new AccountDTO());
        return "account/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@ModelAttribute AccountDTO account,ModelMap model) {
        
        return null;
    }

}