package com.quangphi.service;

import com.quangphi.model.DepartmentDTO;
import com.quangphi.model.StaffsDTO;

public interface StaffsService {

	interface Condition {
		boolean check(StaffsDTO staffsDTO);
	}
	
	StaffsDTO addStaffs(StaffsDTO staffsDTO);
	
	StaffsDTO updateStaffs(StaffsDTO staffsDTO);
	
	Iterable<StaffsDTO> getALLStaffs();
	
	StaffsDTO getByID(String idStaffs);
	
	boolean delete(String idStaffs);
	
	Iterable<StaffsDTO> findStaffsByKeywordAndIdDepartment(String idDepartment,String keyword);
	
	Iterable<StaffsDTO> findStaffsByKeyword(String keyword);
	
	long countStaffBy(DepartmentDTO department);

	long count();

	Iterable<StaffsDTO> findAllStaffsBy(Condition condition);
	
}
