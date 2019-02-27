package com.quangphi.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	
	String FILE_PARENT = System.getProperty("user.dir") + "/src/main/resources/static/img";
	
	static String getPath(String filename) {
		return StorageService.FILE_PARENT + "/" + filename;
	}
	
	boolean writeFile (MultipartFile multipartFile);

	MultipartFile getMultipartFileTo(String filename);

	String getContentType(String filename);

}
