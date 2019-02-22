package com.quangphi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quangphi.service.StaffsService;

@Controller
@RequestMapping("/staffs")
public class StaffsController {

	@Autowired
	private StaffsService staffsService;
	
	@GetMapping("/delete/department/{idDepartment}/{idStaffs}")
	public String test(ModelMap model, @PathVariable String idStaffs, @PathVariable String idDepartment) {
		staffsService.delete(idStaffs);
		return "redirect:/department/infor/"+ idDepartment;
	}
	
}
