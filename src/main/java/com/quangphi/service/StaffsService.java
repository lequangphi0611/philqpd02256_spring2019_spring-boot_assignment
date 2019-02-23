package com.quangphi.service;

import com.quangphi.model.StaffsDTO;

public interface StaffsService {
	
	StaffsDTO addStaffs(StaffsDTO staffsDTO);
	
	StaffsDTO updateStaffs(StaffsDTO staffsDTO);
	
	Iterable<StaffsDTO> getALLStaffs();
	
	StaffsDTO getByID(String idStaffs);
	
	boolean delete(String idStaffs);
	
	Iterable<StaffsDTO> findStaffsByKeywordAndIdDepartment(String idDepartment,String keyword);
	
}
