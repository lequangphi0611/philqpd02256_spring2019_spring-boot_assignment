package com.quangphi.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import com.quangphi.service.StorageService;


@Service
public class StorageServiceImpl implements StorageService {

	@Override
	public boolean writeFile(MultipartFile multipartFile) {
		try {
			String url = StorageService.getPath(multipartFile.getOriginalFilename());
			InputStream inputStream = multipartFile.getInputStream();
			Files.copy(inputStream, Paths.get(url), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public MultipartFile getMultipartFileTo(String filename) {
		String url = StorageService.getPath(filename);
		Path path = Paths.get(url);
		String contentType = "image/" + getContentType(filename);
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		}catch(IOException ex) {
			ex.fillInStackTrace();
		}
		return new MockMultipartFile(filename, filename, contentType, content);
	}

	@Override
	public String getContentType(String filename) {
		int i = filename.lastIndexOf(".");
		if (i < 0) {
			return "";
		}
		return filename.substring(i + 1);
	}

}
