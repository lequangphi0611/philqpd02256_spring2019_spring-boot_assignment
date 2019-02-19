package com.quangphi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.quangphi.entity.Department;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.DepartmentDTO;
import com.quangphi.repository.DepartmentRepository;
import com.quangphi.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        if (departmentRepository.existsById(departmentDTO.getIdDepartment())) {
            throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
                    + departmentDTO.getIdDepartment() + "\" already exists ! ");
        } else if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
            throw new ExistsException("Error : " + Department.class.getName() + " width departmentName = \""
                    + departmentDTO.getDepartmentName() + "\" already exists ! ");
        }
        departmentRepository.save(departmentDTO.toDepartment());
        return departmentDTO;
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        if (!departmentRepository.existsById(departmentDTO.getIdDepartment())) {
            throw new ExistsException("Error : " + Department.class.getName() + " width idDepartment = \""
                    + departmentDTO.getIdDepartment() + "\" does not exists ! ");

        } else if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
            Department department = departmentRepository.findById(departmentDTO.getIdDepartment()).get();
            if (!department.getDepartmentName().equals(departmentDTO.getDepartmentName())) {
                throw new ExistsException("Error : " + Department.class.getName() + " width departmentName = \""
                        + departmentDTO.getDepartmentName() + "\" already exists ! ");
            }
        }
        departmentRepository.save(departmentDTO.toDepartment());
        return departmentDTO;
    }

    public List<DepartmentDTO> getDepartmentDTOs(Iterable<Department> iterable){
        List<DepartmentDTO> allDepartmentDTOs = new ArrayList<>();
        iterable.forEach((items) -> allDepartmentDTOs.add(DepartmentDTO.parseDepartmentDTO(items)));
        return allDepartmentDTOs;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return getDepartmentDTOs(departmentRepository.findAll());
    }

    @Override
    public boolean delete(String idDepartment) {
        departmentRepository.deleteById(idDepartment);
        return true;
    }

}