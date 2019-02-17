package com.quangphi.repository;

import com.quangphi.entity.Department;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department,String> {

    boolean existsByDepartmentName(String departmentName);
}