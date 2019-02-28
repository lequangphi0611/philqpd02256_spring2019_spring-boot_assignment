package com.quangphi.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.quangphi.model.RecordsDTO;
import com.quangphi.model.StaffsDTO;
import com.quangphi.repository.StaffsRepository;
import com.quangphi.service.DepartmentService;
import com.quangphi.service.RecordsService;
import com.quangphi.service.StaffsService;

//@RestController
@Controller
public class TestController {
	
	@Autowired
	private StaffsService staffsService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private StaffsRepository staffsRepository;

	@Autowired
	private RecordsService recordsService;
	
	@GetMapping("/test")
	public String tesst(ModelMap model) {
		List<RecordsDTO> allRecords = (List<RecordsDTO>)recordsService.getAll();
		Collections.sort(allRecords);
		model.addAttribute("allRecords", allRecords);
		return "staffs/staff-infor";
	}

}
