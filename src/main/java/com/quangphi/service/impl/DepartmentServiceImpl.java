package com.quangphi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.quangphi.entity.Department;
import com.quangphi.exception.DepartmentNameExistsException;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.DepartmentDTO;
import com.quangphi.repository.DepartmentRepository;
import com.quangphi.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    
    interface Parser {
    	DepartmentDTO parse(Department department);
    }

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) throws ExistsException,DepartmentNameExistsException {
        if (departmentRepository.existsById(departmentDTO.getIdDepartment())) {
            throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
                    + departmentDTO.getIdDepartment() + "\" already exists ! ");
        } else if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
            throw new DepartmentNameExistsException("Error : " + Department.class.getName() + " width departmentName = \""
                    + departmentDTO.getDepartmentName() + "\" already exists ! ");
        }
        departmentRepository.save(departmentDTO.toDepartment());
        return departmentDTO;
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) throws ExistsException,DepartmentNameExistsException {
        if (!departmentRepository.existsById(departmentDTO.getIdDepartment())) {
            throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
                    + departmentDTO.getIdDepartment() + "\" does not exists ! ");

        } else if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
            Department department = departmentRepository.findById(departmentDTO.getIdDepartment()).get();
            if (!department.getDepartmentName().equalsIgnoreCase(departmentDTO.getDepartmentName())) {
                throw new DepartmentNameExistsException("Error : " + Department.class.getName() + " width departmentName = \""
                        + departmentDTO.getDepartmentName() + "\" already exists ! ");
            }
            return departmentDTO;
        }
        departmentRepository.save(departmentDTO.toDepartment());
        return departmentDTO;
    }
    
    public List<DepartmentDTO> getDepartmentDTOs(Iterable<Department> iterable,Parser parser){
        List<DepartmentDTO> allDepartmentDTOs = new ArrayList<>();
        iterable.forEach((items) -> allDepartmentDTOs.add(parser.parse(items)));
        return allDepartmentDTOs;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return getDepartmentDTOs(departmentRepository.findAll(), (department) -> DepartmentDTO.parseDepartmentDTO(department));
    }

    @Override
    public boolean delete(String idDepartment) {
    	if(!departmentRepository.existsById(idDepartment)) {
    		return false;
    	}
        departmentRepository.deleteById(idDepartment);
        return true;
    }
    
    @Override
    public DepartmentDTO getById(String idDepartment) {
    	if(!departmentRepository.existsById(idDepartment)) {
    		throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
                    + idDepartment + "\" does not exists ! ");
    	}
    	return DepartmentDTO.parseDepartmentDTO(departmentRepository.findById(idDepartment).get());
    }
    
    @Override
    public Iterable<DepartmentDTO> getAllDepartmentWidthOutFetchStaffs() {
    	return getDepartmentDTOs(departmentRepository.findAll(), 
    			(department) -> DepartmentDTO.parseDepartmentDTOWidthOutFetchStaffs(department));
    }

}