package com.quangphi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quangphi.model.StaffsDTO;
import com.quangphi.service.AccountService;
import com.quangphi.service.DepartmentService;
import com.quangphi.service.StaffsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@Autowired
	DepartmentService departmentService;

	@Autowired
	StaffsService staffsService;

	@Autowired
	AccountService accountService;

	final Map<String, Object> allAttributeCount() {
		Map<String, Object> allAttribute = new HashMap<>();
		allAttribute.put("count_department", departmentService.count());
		allAttribute.put("count_staffs", staffsService.count());
		allAttribute.put("count_account", accountService.count());
		return allAttribute;
	}

	@GetMapping("/index.html")
	public String home(ModelMap model) {
		model.addAllAttributes(allAttributeCount());
		List<StaffsDTO> allStaffs = new ArrayList<>();
		for (StaffsDTO staffs : staffsService.getALLStaffs()) {
			if (allStaffs.size() == 10) break;
			allStaffs.add(staffs);
		}
		model.addAttribute("allStaffs", allStaffs);
		return "index";
	}
}