package com.quangphi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quangphi.service.StaffsService;

@Controller
@RequestMapping("/staffs")
public class StaffsController {

	@Autowired
	private StaffsService staffsService;
	
	@GetMapping("/test")
	public String test(ModelMap model) {
		model.addAttribute("allStaffs", staffsService.getALLStaffs());
		return "staffs/staffs-show-data";
	}
}
