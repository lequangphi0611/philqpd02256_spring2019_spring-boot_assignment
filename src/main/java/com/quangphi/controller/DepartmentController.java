package com.quangphi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quangphi.exception.DepartmentNameExistsException;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.DepartmentDTO;
import com.quangphi.service.DepartmentService;
import com.quangphi.service.StaffsService;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private StaffsService staffsService;

	@GetMapping
	public String home(ModelMap model) {
		model.addAttribute("allDepartments", departmentService.getAllDepartments());
		model.addAttribute("department", new DepartmentDTO());
		return "department/department-management";
	}

	@GetMapping("/add")
	public String add() {
		return "redirect:/department";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute("department") @Valid DepartmentDTO department, BindingResult bindingResult,
			ModelMap model) {
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}
			departmentService.addDepartment(department);
			return "redirect:/department";
		} catch (ExistsException e) {
			model.addAttribute("idExists", "Mã phòng ban đã tồn tại !");
		} catch (DepartmentNameExistsException e) {
			model.addAttribute("nameExists", "Tên phòng ban đã tồn tại !");
		} catch (Exception e) {
		}
		model.addAttribute("department", department);
		model.addAttribute("allDepartments", departmentService.getAllDepartments());
		return "department/department-management";
	}

	@GetMapping("/delete/{idDepartment}")
	public String del(@PathVariable String idDepartment) {
		departmentService.delete(idDepartment);
		return "redirect:/department";
	}

	@GetMapping("/infor/{idDepartment}")
	public String infor(@PathVariable String idDepartment, ModelMap model) {
		DepartmentDTO departmentDTO = departmentService.getById(idDepartment);
		model.addAttribute("department", departmentDTO);
		model.addAttribute("allStaffs", departmentDTO.getAllStaffs());
		model.addAttribute("uriDelStaffs", "/staffs/delete/department/" + idDepartment + "/");
		return "department/department-infor";
	}

	@GetMapping("/edit/{idDepartment}")
	public String edit(@PathVariable String idDepartment) {
		return "redirect:/department/infor/" + idDepartment;
	}

	@PostMapping("/edit/{idDepartment}")
	public String edit(@ModelAttribute("department") @Valid DepartmentDTO department, BindingResult bindingResult,
			@PathVariable String idDepartment, ModelMap model) {
		String message = null;
		try {
			if (bindingResult.hasErrors()) {
				message = "Không được để trống !";
			} else {
				department.setIdDepartment(idDepartment);
				departmentService.updateDepartment(department);
				return "redirect:/department/infor/" + idDepartment;
			}
		} catch (DepartmentNameExistsException e) {
			message = "Tên phòng ban đã tồn tại !";
		}
		model.addAttribute("nameError", message);
		return infor(idDepartment, model);
	}

	@GetMapping("/find/staffs/{idDepartment}")
	public String findStaffs(@PathVariable String idDepartment, @RequestParam(required = false) String keyword,
			ModelMap model) {
		if (keyword == null || keyword.isEmpty()) {
			return "redirect:/department/infor/" + idDepartment;
		}
		model.addAttribute("department", departmentService.getById(idDepartment));
		model.addAttribute("allStaffs", staffsService.findStaffsByKeywordAndIdDepartment(idDepartment, keyword));
		model.addAttribute("search_key", keyword);
		return "department/department-infor";
	}

}
