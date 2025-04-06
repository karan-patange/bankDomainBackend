package com.example.bankdomain.entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class BranchPendingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long mobileNumber;
    private String panNumber;
    private String aadharNumber;
    private String emailId;
    private String accountType;
    private String city;
    private String state;
    private Long pinCode;

    private String remark;


    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] customerPhoto;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] signaturePhoto;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] aadharCard;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] panCard;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }


    public byte[] getCustomerPhoto() {
        return customerPhoto;
    }

    public void setCustomerPhoto(byte[] customerPhoto) {
        this.customerPhoto = customerPhoto;
    }

    public byte[] getSignaturePhoto() {
        return signaturePhoto;
    }

    public void setSignaturePhoto(byte[] signaturePhoto) {
        this.signaturePhoto = signaturePhoto;
    }

    public byte[] getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(byte[] aadharCard) {
        this.aadharCard = aadharCard;
    }

    public byte[] getPanCard() {
        return panCard;
    }

    public void setPanCard(byte[] panCard) {
        this.panCard = panCard;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BranchPendingRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", panNumber='" + panNumber + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pinCode=" + pinCode +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}
