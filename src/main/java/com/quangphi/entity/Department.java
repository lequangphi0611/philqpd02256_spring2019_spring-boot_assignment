package com.quangphi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department implements Serializable {

	private static final long serialVersionUID = 8463305366385972536L;
	
	private String idDepartment;
	private String departmentName;

	private List<Staffs> staffs = new ArrayList<>();

	public Department(String idDepartment, String departmentName) {
		this.idDepartment = idDepartment;
		this.departmentName = departmentName;
	}

	public Department(){}

	@Id
	@Column(name = "id", length = 10)
	public String getIdDepartment() {
		return idDepartment;
	}

	public void setIdDepartment(String idDepartment) {
		this.idDepartment = idDepartment;
	}

	@Column(columnDefinition = "NVARCHAR(50)", unique = true,nullable=false)
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department", cascade = CascadeType.ALL)
	public List<Staffs> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<Staffs> staffs) {
		this.staffs = staffs;
	}

}
