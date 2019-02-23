package com.quangphi.model;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.annotation.DateTimeFormat;

import com.quangphi.entity.Staffs;

public class StaffsDTO {
	
	private String idStaffs;
    private String staffsName;
    private Date birthday;
    private Gender gender = Gender.MALE;
    private String photo;
    private String email;
    private String phone;
    private Long salary = 0L;
    private String notes;
    
    private DepartmentDTO department;
    
    public static StaffsDTO parseStaffsDTO(Staffs staffsEntity) {
    	StaffsDTO staffsDTO = new StaffsDTO();
    	staffsDTO.setIdStaffs(staffsEntity.getIdStaffs());
    	staffsDTO.setStaffsName(staffsEntity.getStaffsName());
    	staffsDTO.setBirthday(staffsEntity.getBirthDay());
    	staffsDTO.setGender(staffsEntity.isGender() ? Gender.MALE : Gender.FEMALE);
    	staffsDTO.setPhoto(staffsEntity.getPhoto());
    	staffsDTO.setEmail(staffsEntity.getEmail());
    	staffsDTO.setPhone(staffsEntity.getPhone());
    	staffsDTO.setSalary(staffsEntity.getSalary());
    	staffsDTO.setNotes(staffsEntity.getNotes());
    	staffsDTO.setDepartment(DepartmentDTO.parseDepartmentDTO(staffsEntity.getDepartment()));
    	return staffsDTO;
    }
    
    public static StaffsDTO parseStaffsDTO(Staffs staffsEntity, DepartmentDTO department) {
    	StaffsDTO staffsDTO = new StaffsDTO();
    	staffsDTO.setIdStaffs(staffsEntity.getIdStaffs());
    	staffsDTO.setStaffsName(staffsEntity.getStaffsName());
    	staffsDTO.setBirthday(staffsEntity.getBirthDay());
    	staffsDTO.setGender(staffsEntity.isGender() ? Gender.MALE : Gender.FEMALE);
    	staffsDTO.setPhoto(staffsEntity.getPhoto());
    	staffsDTO.setEmail(staffsEntity.getEmail());
    	staffsDTO.setPhone(staffsEntity.getPhone());
    	staffsDTO.setSalary(staffsEntity.getSalary());
    	staffsDTO.setNotes(staffsEntity.getNotes());
    	staffsDTO.setDepartment(department);
    	return staffsDTO;
    }
    
    public StaffsDTO() {
	}

	public StaffsDTO(String idStaffs, String staffsName, Date birthday, Gender gender, String photo, String email,
			String phone, Long salary, String notes, DepartmentDTO department) {
		super();
		this.idStaffs = idStaffs;
		this.staffsName = staffsName;
		this.birthday = birthday;
		this.gender = gender;
		this.photo = photo;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.notes = notes;
		this.department = department;
	}

	public String getIdStaffs() {
		return idStaffs;
	}

	public void setIdStaffs(String idStaffs) {
		this.idStaffs = idStaffs;
	}

	public String getStaffsName() {
		return staffsName;
	}

	public void setStaffsName(String staffsName) {
		this.staffsName = staffsName;
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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
		staffEntity.setStaffsName(this.staffsName);
		staffEntity.setBirthDay(this.birthday);
		staffEntity.setGender(this.gender == Gender.MALE);
		staffEntity.setPhoto(this.photo);
		staffEntity.setEmail(this.email);
		staffEntity.setPhone(this.phone);
		staffEntity.setSalary(this.salary);
		staffEntity.setNotes(this.notes);
		staffEntity.setDepartment(this.department.toDepartment());
		return staffEntity;
	}
	
	public String getSalaryCurrency() {
		Locale locale= new Locale("vi","VN");
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		return numberFormat.format(this.salary);
	}
	
}
