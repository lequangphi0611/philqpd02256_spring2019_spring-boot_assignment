package com.quangphi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Staffs implements Serializable {

	private static final long serialVersionUID = 7641864034440537062L;
	
	private String idStaffs;
    private String staffsName;
    private Date birthDay;
    private boolean gender;
    private String photo;
    private String email;
    private String phone;
    private Long salary;
    private String notes;

    private Department department;
    private List<Records> records = new ArrayList<>();

    public Staffs() {
    }

    public Staffs(String idStaffs, String staffsName,Date birthDay , boolean gender, String photo, String email, String phone,
            Long salary, String notes) {
        this.idStaffs = idStaffs;
        this.staffsName = staffsName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.notes = notes;
    }

    @Id
    @Column(length=10)
    public String getIdStaffs() {
        return idStaffs;
    }

    public void setIdStaffs(String idStaffs) {
        this.idStaffs = idStaffs;
    }

    @Column(columnDefinition="NVARCHAR(50)",nullable=false)
    public String getStaffsName() {
        return staffsName;
    }

    public void setStaffsName(String staffsName) {
        this.staffsName = staffsName;
    }
    
    @Temporal(TemporalType.DATE)
    public Date getBirthDay() {
		return birthDay;
	}
    
    public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Column(nullable=true)
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

    @ColumnDefault("0")
    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @Column(columnDefinition="TEXT")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idDepartment")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(mappedBy="staffs",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    public List<Records> getRecords() {
        return this.records;
    }

    public void setRecords(List<Records> records) {
        this.records = records;
    }

	@Override
	public String toString() {
		return "Staffs [idStaffs=" + idStaffs + ", staffsName=" + staffsName + ", birthDay=" + birthDay + ", gender="
				+ gender + ", photo=" + photo + ", email=" + email + ", phone=" + phone + ", salary=" + salary
				+ ", notes=" + notes + ", department=" + department + ", records=" + records + "]";
	}
    
    

}