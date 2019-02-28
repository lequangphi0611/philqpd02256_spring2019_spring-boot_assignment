package com.quangphi.controller;

import java.util.HashMap;
import java.util.Map;

import com.quangphi.model.DepartmentDTO;
import com.quangphi.model.Gender;
import com.quangphi.model.RecordsDTO;
import com.quangphi.model.StaffsDTO;
import com.quangphi.service.DepartmentService;
import com.quangphi.service.StaffsService;
import com.quangphi.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/staffs")
public class StaffsController {

	@Autowired
	private StaffsService staffsService;

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StorageService storageService;

	final Map<String, Object> attributeURLEditAndDel = new HashMap<String, Object>() {
		{
			put("urlEdit", "/staffs/edit/");
			put("urlDelStaffs", "/staffs/delete/");
			put("urlRecords","/records/");
		}
	};

	public Map<String, Object> getAttributeinforStaffPageBasic(StaffsDTO staffsDTO) {
		Map<String, Object> allAttribute = new HashMap<>();
		allAttribute.put("staffs", staffsDTO);
		allAttribute.put("allRecords", staffsDTO.getRecords());
		return allAttribute;
	}

	@GetMapping
	public String home(ModelMap model) {
		model.addAttribute("allStaffs", staffsService.getALLStaffs());
		model.addAllAttributes(attributeURLEditAndDel);
		return "staffs/staffs-management";
	}

	public Map<String, Object> getAttributeForm(StaffsDTO staffsDTO, String requestURL, String action) {
		Map<String, Object> allAttribute = new HashMap<String, Object>();
		allAttribute.put("staffs", staffsDTO);
		allAttribute.put("genders", Gender.values());
		allAttribute.put("requestURL", requestURL);
		allAttribute.put("action", action);
		allAttribute.put("allDepartments", departmentService.getAllDepartmentWithOutFetchStaffs());
		return allAttribute;
	}

	public ModelMap initForm(ModelMap model, StaffsDTO staffsDTO, String requestURL, String action) {
		model.addAllAttributes(getAttributeForm(staffsDTO, requestURL, action));
		return model;
	}

	private StaffsDTO getStaffTo(StaffsDTO staffsDTO, String gender, String idDepartment) {
		staffsDTO.setGender(Gender.parseGender(gender));
		staffsDTO.setDepartment(departmentService.getByIdWithOutFetchStaffs(idDepartment));
		return staffsDTO;
	}

	public String addStaffs(StaffsDTO staffsDTO, ModelMap model, String gender, String idDepartment, String requestURL,
			String action) {
		storageService.writeFile(staffsDTO.getPhoto());
		staffsService.addStaffs(getStaffTo(staffsDTO, gender, idDepartment));
		initForm(model, new StaffsDTO(), requestURL, action);
		return "staffs/add-or-update-staffs";
	}

	@GetMapping("/add/department")
	public String addOfDepartment(ModelMap model) {
		DepartmentDTO department = departmentService.getAllDepartments().get(0);
		String requestURL = "/department/infor/" + department.getIdDepartment();
		String action = "/staffs/add/department";
		initForm(model, new StaffsDTO(), requestURL, action);
		return "staffs/add-or-update-staffs";
	}

	@PostMapping("/add/department")
	public String addOfDepartment(@ModelAttribute StaffsDTO staffs, ModelMap model, @RequestParam String gender,
			@RequestParam String idDepartment) {
		String requestURL = "/department/infor/" + idDepartment;
		String action = "/staffs/add/department";
		return addStaffs(staffs, model, gender, idDepartment, requestURL, action);
	}

	@GetMapping("/add")
	public String add(ModelMap model) {
		String requestURL = "/staffs";
		String action = "/staffs/add";
		initForm(model, new StaffsDTO(), requestURL, action);
		return "staffs/add-or-update-staffs";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute StaffsDTO staffs, ModelMap model, @RequestParam String gender,
			@RequestParam String idDepartment) {
		String requestURL = "/staffs";
		String action = "/staffs/add";
		return addStaffs(staffs, model, gender, idDepartment, requestURL, action);
	}

	@GetMapping("/edit/{idStaffs}")
	public String edit(ModelMap model, @PathVariable String idStaffs) {
		String requestURL = "/staffs";
		String action = "/staffs/edit";
		StaffsDTO staffs = staffsService.getByID(idStaffs);
		System.err.println(staffs.getPhoto().getOriginalFilename());
		initForm(model, staffs, requestURL, action);
		return "staffs/add-or-update-staffs";
	}

	@PostMapping("/edit")
	public String edit(@ModelAttribute StaffsDTO staffs, ModelMap model, @RequestParam String gender,
			@RequestParam String idDepartment) {
		storageService.writeFile(staffs.getPhoto());
		staffsService.updateStaffs(getStaffTo(staffs, gender, idDepartment));
		return "redirect:/staffs";
	}

	@PostMapping("/edit/department")
	public String editOfDepartment(@ModelAttribute StaffsDTO staffs, ModelMap model, @RequestParam String gender,
			@RequestParam String idDepartment) {
		storageService.writeFile(staffs.getPhoto());
		staffsService.updateStaffs(getStaffTo(staffs, gender, idDepartment));
		return "redirect:/department/infor/" + idDepartment;
	}

	@GetMapping("/delete/{idDepartment}/{idStaffs}")
	public String delStaffAndReturnDepartmentPage(ModelMap model, @PathVariable String idStaffs,
			@PathVariable String idDepartment) {
		staffsService.delete(idStaffs);
		return "redirect:/department/infor/" + idDepartment;
	}

	@GetMapping("/delete/{idStaffs}")
	public String deleteStaffs(@PathVariable String idStaffs) {
		staffsService.delete(idStaffs);
		return "redirect:/staffs";
	}

	@GetMapping("/search")
	public String search(@RequestParam(required = false) String keyword, ModelMap model) {
		if (keyword == null || keyword.isEmpty()) return "redirect:/staffs";
		model.addAllAttributes(new HashMap<String, Object>() {
			{
				put("allStaffs", staffsService.findStaffsByKeyword(keyword));
				put("search_key", keyword);
			}
		});
		model.addAllAttributes(attributeURLEditAndDel);
		return "staffs/staffs-management";
	}

	@GetMapping("/{idStaffs}")
	public String showInfor(ModelMap model, @PathVariable String idStaffs) {
		model.addAllAttributes(getAttributeinforStaffPageBasic(staffsService.getByID(idStaffs)));
		return "staffs/staffs-infor";
	}

}
