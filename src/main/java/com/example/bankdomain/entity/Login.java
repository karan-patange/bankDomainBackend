package com.example.bankdomain.entity;

import jakarta.persistence.*;

@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginId;

    private String accountNumber;
    private String passWord;
    private String role;

    @OneToOne
    @JoinColumn(name = "Customer_id", referencedColumnName = "id")
    private Customer customer;

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Login{" +
                "loginId=" + loginId +
                ", accountNumber='" + accountNumber + '\'' +
                ", passWord='" + passWord + '\'' +
                ", role='" + role + '\'' +
                ", customer=" + customer +
                '}';
    }
}
