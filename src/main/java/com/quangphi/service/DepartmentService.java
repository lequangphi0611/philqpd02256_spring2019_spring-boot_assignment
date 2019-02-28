package com.quangphi.service;

import java.util.List;

import com.quangphi.model.DepartmentDTO;

public interface DepartmentService {

    DepartmentDTO addDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO);

    List<DepartmentDTO> getAllDepartments();

    boolean delete(String idDepartment);
    
    DepartmentDTO getByIdWithOutFetchStaffs(String idDepartment);
    
    DepartmentDTO getByIdFetchStaffs(String idDepartment);
    
    Iterable<DepartmentDTO> getAllDepartmentWithOutFetchStaffs();

    long count();

}