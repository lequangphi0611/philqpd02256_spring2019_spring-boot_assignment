package com.quangphi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.quangphi.entity.Department;

import org.hibernate.validator.constraints.Length;

public class DepartmentDTO {

	@NotEmpty(message = "Không được để trống mã phòng ban")
	@Length(min = 5,max = 10, message = "Mã phòng ban có ít nhất 5 ký tự và ít hơn 10 ký tự")
	private String idDepartment;
	@NotEmpty(message = "Không được để trống tên phòng ban")
	private String departmentName;
	
	private long countStaffs;

	private List<StaffsDTO> allStaffs = new ArrayList<>();

	public DepartmentDTO() {
	}

	public DepartmentDTO(String idDepartment, String departmentName) {
		this.idDepartment = idDepartment;
		this.departmentName = departmentName;
	}

	public static DepartmentDTO parseDepartmentDTO(Department department) {
		DepartmentDTO departmentDTO = parseDepartmentDTOWidthOutFetchStaffs(department);
		department.getStaffs()
				.forEach(items -> departmentDTO.getAllStaffs().add(StaffsDTO.parseStaffsDTO(items)));
		Collections.sort(departmentDTO.allStaffs);
		return departmentDTO;
	}
	
	public static DepartmentDTO parseDepartmentDTOWidthOutFetchStaffs(Department department) {
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

	public List<StaffsDTO> getAllStaffs() {
		return allStaffs;
	}

	public void setAllStaffs(List<StaffsDTO> allStaffs) {
		this.allStaffs = allStaffs;
	}
	
	public long getCountStaffs() {
		return countStaffs;
	}
	
	public void setCountStaffs(long countStaffs) {
		this.countStaffs = countStaffs;
	}

	@Override
	public String toString() {
		return this.departmentName;
	}

}