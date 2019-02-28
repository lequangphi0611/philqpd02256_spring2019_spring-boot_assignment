package com.quangphi.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.quangphi.entity.Staffs;
import com.quangphi.service.impl.StorageServiceImpl;

public class StaffsDTO implements Comparable<StaffsDTO> {

	public static final int LEVEL_MAX = 10;
	public static final int LEVEL_MIN = 1;

	private String idStaffs;
	private String staffsName;
	private Date birthday;
	private Gender gender = Gender.MALE;
	private MultipartFile photo;
	private String email;
	private String phone;
	private Long salary = 0L;
	private String notes;

	private DepartmentDTO department;

	private List<RecordsDTO> records = new ArrayList<>();

	public static StaffsDTO parseStaffsDTO(Staffs staffsEntity) {
		StaffsDTO staffsDTO = new StaffsDTO();
		staffsDTO.setIdStaffs(staffsEntity.getIdStaffs());
		staffsDTO.setStaffsName(staffsEntity.getStaffsName());
		staffsDTO.setBirthday(staffsEntity.getBirthDay());
		staffsDTO.setGender(staffsEntity.isGender() ? Gender.MALE : Gender.FEMALE);
		staffsDTO.setPhoto(new StorageServiceImpl().getMultipartFileTo(staffsEntity.getPhoto()));
		staffsDTO.setEmail(staffsEntity.getEmail());
		staffsDTO.setPhone(staffsEntity.getPhone());
		staffsDTO.setSalary(staffsEntity.getSalary());
		staffsDTO.setNotes(staffsEntity.getNotes());
		staffsDTO.setDepartment(DepartmentDTO.parseDepartmentDTOWidthOutFetchStaffs(staffsEntity.getDepartment()));
		List<RecordsDTO> records = new ArrayList<>();
		staffsEntity.getRecords().forEach((recordsEntity) -> {
			records.add(RecordsDTO.parseRecordsDTO(recordsEntity));
		});
		staffsDTO.setRecords(records);
		return staffsDTO;
	}

	public StaffsDTO() {
	}

	public StaffsDTO(String idStaffs, String staffsName, Date birthday, Gender gender, MultipartFile photo,
			String email, String phone, Long salary, String notes, DepartmentDTO department) {
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

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
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

	public List<RecordsDTO> getRecords() {
		return records;
	}

	public void setRecords(List<RecordsDTO> records) {
		this.records = records;
	}

	public Staffs toStaffsEntity() {
		Staffs staffEntity = new Staffs();
		staffEntity.setIdStaffs(this.idStaffs);
		staffEntity.setStaffsName(this.staffsName);
		staffEntity.setBirthDay(this.birthday);
		staffEntity.setGender(this.gender == Gender.MALE);
		if (this.photo.getOriginalFilename() == null || this.photo.getOriginalFilename().isEmpty()) {
			staffEntity.setPhoto(staffEntity.isGender() ? "Male.jpg" : "Female.jpg");
		} else {
			staffEntity.setPhoto(this.photo.getOriginalFilename());
		}
		staffEntity.setEmail(this.email);
		staffEntity.setPhone(this.phone);
		staffEntity.setSalary(this.salary);
		staffEntity.setNotes(this.notes);
		staffEntity.setDepartment(this.department.toDepartment());
		return staffEntity;
	}

	public String getSalaryCurrency() {
		Locale locale = new Locale("vi", "VN");
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		return numberFormat.format(this.salary);
	}

	public int getLevel() {
		int level = StaffsDTO.LEVEL_MIN;
		for (RecordsDTO items : this.records) {
			if (items.getType() && level < StaffsDTO.LEVEL_MAX) {
				level++;
			} else if (level > StaffsDTO.LEVEL_MIN) {
				level--;
			}
		}
		return level;
	}

	public int getAchievement() {
		int result = 0;
		for(RecordsDTO recordsDTO : this.records) {
			if(recordsDTO.getType()) {
				result++;
			}
		}
		return result;
	}

	public int getDiscipline() {
		int result = 0;
		for(RecordsDTO recordsDTO : this.records) {
			if(!recordsDTO.getType()) {
				result--;
			}
		}
		return result;
	}

	@Override
	public int compareTo(StaffsDTO o) {
		int level_this = this.getLevel();
		int level_o = o.getLevel();
		if (level_this > level_o) {
			return -1;
		} else if (level_this < level_o) {
			return 1;
		}
		return 0;
	}
}
