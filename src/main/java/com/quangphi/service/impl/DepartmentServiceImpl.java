package com.quangphi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quangphi.entity.Department;
import com.quangphi.exception.DepartmentNameExistsException;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.DepartmentDTO;
import com.quangphi.repository.DepartmentRepository;
import com.quangphi.service.DepartmentService;
import com.quangphi.util.ConvertListSupport;
import com.quangphi.util.Parsers;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ConvertListSupport<DepartmentDTO, Department> convertListSupport;

	@Override
	public DepartmentDTO addDepartment(DepartmentDTO departmentDTO)
			throws ExistsException, DepartmentNameExistsException {
		if (departmentRepository.existsById(departmentDTO.getIdDepartment())) {
			throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
					+ departmentDTO.getIdDepartment() + "\" already exists ! ");
		} else if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
			throw new DepartmentNameExistsException("Error : " + Department.class.getName()
					+ " width departmentName = \"" + departmentDTO.getDepartmentName() + "\" already exists ! ");
		}
		departmentRepository.save(departmentDTO.toDepartment());
		return departmentDTO;
	}

	@Override
	public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO)
			throws ExistsException, DepartmentNameExistsException {
		if (!departmentRepository.existsById(departmentDTO.getIdDepartment())) {
			throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
					+ departmentDTO.getIdDepartment() + "\" does not exists ! ");

		} else if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
			Department department = departmentRepository.findById(departmentDTO.getIdDepartment()).get();
			if (!department.getDepartmentName().equalsIgnoreCase(departmentDTO.getDepartmentName())) {
				throw new DepartmentNameExistsException("Error : " + Department.class.getName()
						+ " width departmentName = \"" + departmentDTO.getDepartmentName() + "\" already exists ! ");
			}
			return departmentDTO;
		}
		departmentRepository.save(departmentDTO.toDepartment());
		return departmentDTO;
	}

	@Override
	public List<DepartmentDTO> getAllDepartments() {
		return (List<DepartmentDTO>) convertListSupport.converting(departmentRepository.findAll(),
				DepartmentDTO::parseDepartmentDTO);
	}

	@Override
	public boolean delete(String idDepartment) {
		if (!departmentRepository.existsById(idDepartment)) {
			return false;
		}
		departmentRepository.deleteById(idDepartment);
		return true;
	}

	private DepartmentDTO getByID(String idDepartment, Parsers<DepartmentDTO, Department> parser) {
		if (!departmentRepository.existsById(idDepartment)) {
			throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
					+ idDepartment + "\" does not exists ! ");
		}
		return parser.parse(departmentRepository.findById(idDepartment).get());
	}

	@Override
	public DepartmentDTO getByIdWithOutFetchStaffs(String idDepartment) {
		return getByID(idDepartment, DepartmentDTO::parseDepartmentDTOWidthOutFetchStaffs);
	}

	@Override
	public DepartmentDTO getByIdFetchStaffs(String idDepartment) {
		return getByID(idDepartment, DepartmentDTO::parseDepartmentDTO);
	}

	@Override
	public Iterable<DepartmentDTO> getAllDepartmentWithOutFetchStaffs() {
		return convertListSupport.converting(departmentRepository.findAll(),
				DepartmentDTO::parseDepartmentDTOWidthOutFetchStaffs);
	}

	@Override
	public long count() {
		return departmentRepository.count();
	}

}