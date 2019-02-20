package com.quangphi.model;

import com.quangphi.entity.Staffs;

public class StaffsDTO {
	
	private String idStaffs;
    private String staffName;
    private boolean gender;
    private String photo;
    private String email;
    private String phone;
    private Long salary;
    private String notes;
    
    private DepartmentDTO department;
    
    public static StaffsDTO parseStaffsDTO(Staffs staffsEntity) {
    	StaffsDTO staffsDTO = new StaffsDTO();
    	staffsDTO.setIdStaffs(staffsEntity.getIdStaffs());
    	staffsDTO.setStaffName(staffsEntity.getStaffName());
    	staffsDTO.setGender(staffsEntity.isGender());
    	staffsDTO.setPhoto(staffsEntity.getPhoto());
    	staffsDTO.setEmail(staffsEntity.getEmail());
    	staffsDTO.setPhone(staffsEntity.getPhone());
    	staffsDTO.setSalary(staffsEntity.getSalary());
    	staffsDTO.setNotes(staffsEntity.getNotes());
    	staffsDTO.setDepartment(DepartmentDTO.parseDepartmentDTO(staffsEntity.getDepartment()));
    	return staffsDTO;
    }
    
    public StaffsDTO() {
	}

	public StaffsDTO(String idStaffs, String staffName, boolean gender, String photo, String email, String phone,
			Long salary, String notes) {
		super();
		this.idStaffs = idStaffs;
		this.staffName = staffName;
		this.gender = gender;
		this.photo = photo;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.notes = notes;
	}

	public String getIdStaffs() {
		return idStaffs;
	}

	public void setIdStaffs(String idStaffs) {
		this.idStaffs = idStaffs;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public DepartmentDTO getDepartment() {
		return department;
	}
	
	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}
   
	public Staffs toStaffsEntity() {
		Staffs staffEntity = new Staffs();
		staffEntity.setIdStaffs(this.idStaffs);
		staffEntity.setStaffName(this.staffName);
		staffEntity.setGender(this.gender);
		staffEntity.setPhoto(this.photo);
		staffEntity.setEmail(this.email);
		staffEntity.setPhone(this.phone);
		staffEntity.setSalary(this.salary);
		staffEntity.setNotes(this.notes);
		staffEntity.setDepartment(this.department.toDepartment());
		return staffEntity;
	}
}
