package com.quangphi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Staffs implements Serializable {

    private String idStaffs;
    private String StaffName;
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

    public Staffs(String idStaffs, String StaffName, boolean gender, String photo, String email, String phone,
            Long salary, String notes) {
        this.idStaffs = idStaffs;
        this.StaffName = StaffName;
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

    @Column(columnDefinition="NVARCHAR(50)")
    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
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

    @ManyToOne
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

}