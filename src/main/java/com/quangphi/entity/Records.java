package com.quangphi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Records implements Serializable {

	private static final long serialVersionUID = 4114525093536706913L;
	
	private int idRecords;
    private boolean type;
    private String reason;
    private Date date;

    private Staffs staffs;

    public Records(){}

    public Records(int idRecords, boolean type, String reason, Date date) {
        this.idRecords = idRecords;
        this.type = type;
        this.reason = reason;
        this.date = date;
    }


    public Records(boolean type, String reason, Date date, Staffs staffs) {
        this.type = type;
        this.reason = reason;
        this.date = date;
        this.staffs = staffs;
    }


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getIdRecords() {
        return this.idRecords;
    }

    public void setIdRecords(int idRecords) {
        this.idRecords = idRecords;
    }

    @Column
    public boolean isType() {
        return this.type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Column(columnDefinition="TEXT")
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name="idStaff")
    public Staffs getStaffs() {
        return this.staffs;
    }

    public void setStaffs(Staffs staffs) {
        this.staffs = staffs;
    }
}