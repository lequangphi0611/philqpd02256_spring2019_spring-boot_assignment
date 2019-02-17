package com.quangphi.service.impl;

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
        }
        if (departmentRepository.existsByDepartmentName(departmentDTO.getDepartmentName())) {
            throw new ExistsException("Error : " + Department.class.getName() + " width departmentName = \""
                    + departmentDTO.getDepartmentName() + "\" already exists ! ");
        }
        return null;
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        return null;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return null;
    }

    @Override
    public boolean delete(String idDepartment) {
        return false;
    }

}