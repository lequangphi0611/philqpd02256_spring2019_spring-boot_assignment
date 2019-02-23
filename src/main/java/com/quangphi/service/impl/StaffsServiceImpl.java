package com.quangphi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quangphi.entity.Department;
import com.quangphi.entity.Staffs;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.StaffsDTO;
import com.quangphi.repository.StaffsRepository;
import com.quangphi.service.StaffsService;

@Service
public class StaffsServiceImpl implements StaffsService {
	
	@Autowired
	private StaffsRepository staffsRepository;
	
	public Iterable<StaffsDTO> GetStaffsDTOTo(Iterable<Staffs> allStaffsEntity){
		List<StaffsDTO> allStaffDTOs = new ArrayList<>();
		allStaffsEntity.forEach(staff -> allStaffDTOs.add(StaffsDTO.parseStaffsDTO(staff)));
		return allStaffDTOs;
	}

	@Override
	public StaffsDTO addStaffs(StaffsDTO staffsDTO) {
		if(staffsRepository.existsById(staffsDTO.getIdStaffs())) {
			throw new ExistsException("Error : " + Staffs.class.getName() + " width idStaffs = \""
                    + staffsDTO.getIdStaffs() + "\" already exists ! ");
		}
		staffsRepository.save(staffsDTO.toStaffsEntity());
		return staffsDTO;
	}

	@Override
	public StaffsDTO updateStaffs(StaffsDTO staffsDTO) {
		if(!staffsRepository.existsById(staffsDTO.getIdStaffs())) {
			throw new ExistsException("Error : " + Staffs.class.getName() + " width idStaffs = \""
                    + staffsDTO.getIdStaffs() + "\" does not exists ! ");
		}
		staffsRepository.save(staffsDTO.toStaffsEntity());
		return staffsDTO;
	}

	@Override
	public Iterable<StaffsDTO> getALLStaffs() {
		return GetStaffsDTOTo(staffsRepository.findAll());
	}

	@Override
	public StaffsDTO getByID(String idStaffs) {
		return StaffsDTO.parseStaffsDTO(staffsRepository.findById(idStaffs).get());
	}

	@Override
	public boolean delete(String idStaffs) {
		if(!staffsRepository.existsById(idStaffs)) {
			return false;
		}
		staffsRepository.deleteById(idStaffs);
		return true;
	}
	
	@Override
	public Iterable<StaffsDTO> findStaffsByKeywordAndIdDepartment(String idDepartment, String keyword) {
		Department department = new Department(idDepartment, null);
		List<StaffsDTO> result = 
				(List<StaffsDTO>) GetStaffsDTOTo(staffsRepository
						.findAllStaffsByDepartmentAndIdStaffsContaining(department, keyword));
		List<StaffsDTO> tempStaffsDTO = (List<StaffsDTO>) GetStaffsDTOTo( staffsRepository
				.findAllStaffsByDepartmentAndStaffsNameContaining(department, keyword));
		if(!tempStaffsDTO.isEmpty()) {
			tempStaffsDTO.forEach(
					(itemsStaffs) -> {
						if(!existsStaffsIn(itemsStaffs, result)) {
							result.add(itemsStaffs);
						}
					}
			);
		}
		return result;
	}
	
	boolean existsStaffsIn(StaffsDTO staffsDTO,Iterable<StaffsDTO> allIStaffsDTO) {
		for(StaffsDTO items : allIStaffsDTO) {
			if(items.getIdStaffs().equals(staffsDTO.getIdStaffs())) {
				return true;
			}
		}
		return false;
	}

}
