package com.quangphi.model;

import javax.validation.constraints.NotEmpty;

import com.quangphi.entity.Department;

public class DepartmentDTO {

	@NotEmpty(message = "Không được để trống mã phòng ban")
	private String idDepartment;
	@NotEmpty(message = "Không được để trống tên phòng ban")
	private String departmentName;

	public DepartmentDTO() {
	}

	public DepartmentDTO(String idDepartment, String departmentName) {
		this.idDepartment = idDepartment;
		this.departmentName = departmentName;
	}

	public static DepartmentDTO parseDepartmentDTO(Department department) {
		return new DepartmentDTO(department.getIdDepartment(), department.getDepartmentName());
	}

	public String getIdDepartment() {
		return this.idDepartment;
	}

	public void setIdDepartment(String idDepartment) {
		this.idDepartment = idDepartment;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Department toDepartment() {
		return new Department(idDepartment, departmentName);
	}

	@Override
	public String toString() {
		return this.departmentName;
	}

}