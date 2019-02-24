package com.quangphi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quangphi.entity.Department;
import com.quangphi.entity.Staffs;

@Repository
public interface StaffsRepository extends CrudRepository<Staffs, String> {

	Iterable<Staffs> findAllStaffsByDepartmentAndIdStaffsContaining(Department department, String idStaffs);

	Iterable<Staffs> findAllStaffsByDepartmentAndStaffsNameContaining(Department department, String staffsName);
	
	long countStaffsByDepartment(Department department);

}
