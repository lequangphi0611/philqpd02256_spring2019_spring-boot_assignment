package com.quangphi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
	@GetMapping("/index.html")
	public String home() {
		return "index";
	}
	
	@GetMapping(value = "/layout")
	public String getMethodName() {
		return "common/layout";
	}
}