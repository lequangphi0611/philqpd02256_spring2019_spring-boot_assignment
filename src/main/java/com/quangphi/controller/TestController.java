package com.quangphi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quangphi.entity.Department;
import com.quangphi.model.DepartmentDTO;
import com.quangphi.model.Gender;
import com.quangphi.model.StaffsDTO;
import com.quangphi.repository.StaffsRepository;
import com.quangphi.service.DepartmentService;
import com.quangphi.service.StaffsService;

@RestController
//@Controller
public class TestController {
	
	@Autowired
	private StaffsService staffsService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StaffsRepository staffsRepository;
	
	@GetMapping(value = "/test")
	public StaffsDTO getMethodName() {
		StaffsDTO staffs = new StaffsDTO();
		staffs.setIdStaffs("ST010");
		staffs.setStaffsName("Quang Phi");
		staffs.setGender(Gender.MALE);
		staffs.setPhone("0773927601");
		staffs.setEmail("SojCute@gmail.com");
		staffs.setNotes("Không có mô tả");
		staffs.setPhoto("default.jpg");
		staffs.setSalary(10888888L);
		staffs.setDepartment(new DepartmentDTO("PB001", null));
		return staffsService.addStaffs(staffs);
	}
	
	@GetMapping("/testIterable")
	@ResponseBody
	public String getALl() {
		staffsRepository.findAllStaffsByDepartmentAndIdStaffsContaining(new Department("PB001",null), "S")
			.forEach(System.out::println)
		;
		return "ddddddd";
	}

}
