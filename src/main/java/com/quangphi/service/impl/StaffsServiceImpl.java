package com.quangphi.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quangphi.entity.Department;
import com.quangphi.entity.Staffs;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.DepartmentDTO;
import com.quangphi.model.StaffsDTO;
import com.quangphi.repository.DepartmentRepository;
import com.quangphi.repository.StaffsRepository;
import com.quangphi.service.StaffsService;
import com.quangphi.util.ConvertListSupport;
import com.quangphi.util.Parsers;

@Service
public class StaffsServiceImpl implements StaffsService {

	@Autowired
	private StaffsRepository staffsRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ConvertListSupport<StaffsDTO, Staffs> convertListSupport;

	public Parsers<StaffsDTO, Staffs> getPasersStaffs() {
		return (staffs) -> StaffsDTO.parseStaffsDTO(staffs);
	}

	@Override
	public StaffsDTO addStaffs(StaffsDTO staffsDTO) {
		if (staffsRepository.existsById(staffsDTO.getIdStaffs())) {
			throw new ExistsException("Error : " + Staffs.class.getName() + " width idStaffs = \""
					+ staffsDTO.getIdStaffs() + "\" already exists ! ");
		}
		staffsRepository.save(staffsDTO.toStaffsEntity());
		return staffsDTO;
	}

	@Override
	public StaffsDTO updateStaffs(StaffsDTO staffsDTO) {
		if (!staffsRepository.existsById(staffsDTO.getIdStaffs())) {
			throw new ExistsException("Error : " + Staffs.class.getName() + " width idStaffs = \""
					+ staffsDTO.getIdStaffs() + "\" does not exists ! ");
		}
		staffsRepository.save(staffsDTO.toStaffsEntity());
		return staffsDTO;
	}

	@Override
	public Iterable<StaffsDTO> getALLStaffs() {
		List<StaffsDTO> list = (List<StaffsDTO>) convertListSupport.converting(staffsRepository.findAll(),
				this.getPasersStaffs());
		Collections.sort(list);
		return list;
	}

	@Override
	public StaffsDTO getByID(String idStaffs) {
		return StaffsDTO.parseStaffsDTO(staffsRepository.findById(idStaffs).get());
	}

	@Override
	public boolean delete(String idStaffs) {
		if (!staffsRepository.existsById(idStaffs)) {
			return false;
		}
		staffsRepository.deleteById(idStaffs);
		return true;
	}

	@Override
	public Iterable<StaffsDTO> findStaffsByKeywordAndIdDepartment(String idDepartment, String keyword) {
		Department department = departmentRepository.findById(idDepartment).get();
		List<StaffsDTO> result = (List<StaffsDTO>) convertListSupport.converting(
				staffsRepository.findAllStaffsByDepartmentAndIdStaffsContaining(department, keyword),
				this.getPasersStaffs());
		List<StaffsDTO> tempStaffsDTO = (List<StaffsDTO>) convertListSupport.converting(
				staffsRepository.findAllStaffsByDepartmentAndStaffsNameContaining(department, keyword),
				this.getPasersStaffs());
		if (!tempStaffsDTO.isEmpty()) {
			tempStaffsDTO.forEach((itemsStaffs) -> {
				if (!existsStaffsIn(itemsStaffs, result)) {
					result.add(itemsStaffs);
				}
			});
		}
		return result;
	}

	@Override
	public Iterable<StaffsDTO> findStaffsByKeyword(String keyword) {
		List<Staffs> allsStaffByIdStaff = (List<Staffs>) staffsRepository.findAllStaffsByIdStaffsContaining(keyword);
		List<StaffsDTO> result = (List<StaffsDTO>) convertListSupport.converting(allsStaffByIdStaff,
				this.getPasersStaffs());
		List<Staffs> tempStaffs = (List<Staffs>) staffsRepository.findAllStaffsByStaffsNameContaining(keyword);
		if (!tempStaffs.isEmpty()) {
			tempStaffs.forEach((staffs) -> {
				StaffsDTO staffDTO = StaffsDTO.parseStaffsDTO(staffs);
				if (!existsStaffsIn(staffDTO, result)) {
					result.add(staffDTO);
				}
			});
		}

		return result;
	}

	private boolean existsStaffsIn(StaffsDTO staffsDTO, Iterable<StaffsDTO> allIStaffsDTO) {
		for (StaffsDTO items : allIStaffsDTO) {
			if (items.getIdStaffs().equals(staffsDTO.getIdStaffs())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public long countStaffBy(DepartmentDTO department) {
		return staffsRepository.countStaffsByDepartment(department.toDepartment());
	}

}
