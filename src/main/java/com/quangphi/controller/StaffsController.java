package com.quangphi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quangphi.model.DepartmentDTO;
import com.quangphi.model.Gender;
import com.quangphi.model.StaffsDTO;
import com.quangphi.service.DepartmentService;
import com.quangphi.service.StaffsService;

@Controller
@RequestMapping("/staffs")
public class StaffsController {

	@Autowired
	private StaffsService staffsService;
	
	@Autowired
	private DepartmentService departmentService;
	
	public ModelMap initForm(ModelMap model, StaffsDTO staffsDTO) {
		model.addAttribute("staffs", staffsDTO);
		model.addAttribute("allDepartments", departmentService.getAllDepartmentWithOutFetchStaffs());
		model.addAttribute("genders",Gender.values());
		return model;
	}
	
	@GetMapping("/add")
	public String addPage(ModelMap model) {
		initForm(model, new StaffsDTO());
		return "staffs/add-staffs";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute StaffsDTO staffsDTO, ModelMap model, 
			@RequestParam String gender, @RequestParam String idDepartment) {
		staffsDTO.setGender(Gender.parseGender(gender));
		staffsDTO.setDepartment(new DepartmentDTO(idDepartment, null));
		staffsService.addStaffs(staffsDTO);
		return "redirect:/staffs/add";
	}
	
	@GetMapping("/delete/department/{idDepartment}/{idStaffs}")
	public String test(ModelMap model, @PathVariable String idStaffs, @PathVariable String idDepartment) {
		staffsService.delete(idStaffs);
		return "redirect:/department/infor/"+ idDepartment;
	}
	
}
