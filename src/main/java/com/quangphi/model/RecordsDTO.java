package com.quangphi.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.quangphi.entity.Records;

public class RecordsDTO implements Comparable<RecordsDTO> {

    private boolean type = true;
    @NotEmpty
    private String reason;
    private Date date;

    {
        date = new Date();
    }

    private StaffsDTO staffsDTO;

    public static RecordsDTO parseRecordsDTO(Records recordsEntity) {
        return new RecordsDTO(recordsEntity.isType(), recordsEntity.getReason(), recordsEntity.getDate());
    }

    public RecordsDTO() {
    }

    public RecordsDTO(boolean type, String reason, Date date) {
        this.type = type;
        this.reason = reason;
        this.date = date;
    }

    public RecordsDTO(boolean type, String reason, Date date, StaffsDTO staffsDTO) {
        this.type = type;
        this.reason = reason;
        this.date = date;
        this.staffsDTO = staffsDTO;
    }

    public boolean isType() {
        return this.type;
    }

    public boolean getType() {
        return this.type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StaffsDTO getStaffsDTO() {
        return staffsDTO;
    }

    public void setStaffsDTO(StaffsDTO staffsDTO) {
        this.staffsDTO = staffsDTO;
    }

    public Records toRecords() {
        return new Records(type, reason, date, staffsDTO.toStaffsEntity());
    }

    @Override
    public int compareTo(RecordsDTO o) {
        long time_this = this.date.getTime();
        long time_o = o.date.getTime();
        if (time_this > time_o) {
            return -1;
        } else if (time_this < time_o) {
            return 1;
        }
        return 0;
    }

}