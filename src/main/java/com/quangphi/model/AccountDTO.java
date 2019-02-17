package com.quangphi.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.quangphi.entity.Account;

public class AccountDTO {

    public static AccountDTO parseAccountDTO(Account account) {
        return new AccountDTO(account.getUsername(), account.getPassword(), account.getFullname());
    }

    @NotEmpty(message = "Không được để trống tên đăng nhập !")
    @Size(min = 5, max = 20, message = "Tên đăng nhập có ít nhất 5 ký tự và không quá 20 ký tự !")
    private String username;
    @NotEmpty(message = "Không được để trống mật khẩu !")
    @Size(min = 3, max = 16, message = "Mật khẩu có ít nhất 3 ký tự và không quá 16 ký tự !")
    private String password;
    @NotEmpty(message = "Không được để trống tên !")
    private String fullname;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Account toAccount() {
        return new Account(this.username, this.password, this.fullname);
    }

}