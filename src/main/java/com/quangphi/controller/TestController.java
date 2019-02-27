package com.quangphi.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.quangphi.repository.StaffsRepository;
import com.quangphi.service.DepartmentService;
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
	
	@GetMapping("/upload")
	public String upload() {
		return "staffs/testUpload";
	}
	
	String path = System.getProperty("user.dir") +"/src/main/resources/static/img/" ;
	
	@PostMapping("/upload")
	@ResponseBody
	public String upload( @RequestParam MultipartFile filename) throws IOException {
		
		return parse(filename.getBytes());
	}
	
	public String parse(byte[] bytes) {
		
		InputStream in = new ByteArrayInputStream(bytes);
		return "";
	}

}
