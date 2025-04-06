package com.example.bankdomain.entity;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private Double amount;

    private String accountNumber;

    private String depositeMode;

    @Column(name = "depositerDetails")
    private String depositeDetails;

    @CreationTimestamp // used for savimg curremt time
    private LocalDateTime time;

    public String getdepositeMode() {
        return depositeMode;
    }

    public void setdepositeMode(String depositeMode) {
        this.depositeMode = depositeMode;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

//    @ManyToOne
//    @JsonBackReference
//    @JoinColumn(name = "FK_account")
//    private Account account;








    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name="account_id")
    private Account account;


    public String getDepositeDetails() {
        return depositeDetails;
    }

    public void setDepositeDetails(String depositeDetails) {
        this.depositeDetails = depositeDetails;
    }



    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }




    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", accountNumber='" + accountNumber + '\'' +
                ", depositeMode='" + depositeMode + '\'' +
                ", depositeDetails='" + depositeDetails + '\'' +
                ", time=" + time +
                ", account=" + account +
                '}';
    }
}
