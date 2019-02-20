package com.quangphi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = 1309273091702458137L;
	
	private String username;
    private String password;
    private String fullname;

    public Account() {}

    public Account(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

    @Id
    @Column(length=20,nullable=false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(length=16,nullable=false)
    @Size(min = 3)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(length=30,nullable=false)
    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    
}