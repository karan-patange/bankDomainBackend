package com.example.bankdomain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private Double amount;

    private String withdrawMode;

    private String withdrawDetails;

    @CreationTimestamp // used for savimg curremt time
    private LocalDateTime time;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name="account_id")
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getWithdrawMode() {
        return withdrawMode;
    }

    public void setWithdrawMode(String withdrawMode) {
        this.withdrawMode = withdrawMode;
    }

    public String getWithdrawDetails() {
        return withdrawDetails;
    }

    public void setWithdrawDetails(String withdrawDetails) {
        this.withdrawDetails = withdrawDetails;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Withdrawal{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", withdrawMode='" + withdrawMode + '\'' +
                ", withdrawDetails='" + withdrawDetails + '\'' +
                ", time=" + time +
                ", account=" + account +
                '}';
    }
}
