package com.example.bankdomain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cus_name")
    private String customerName;

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
    private byte[] panCardpdf;

    private Long mobileNumber;
    private String panCard;
    private boolean deleteCustomer;
    private String adharcard;
    private String emailId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_account")
    private Account account;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Addresses> addresses;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Login login;

    public Customer() {}

    public Customer(String name, String mobileNumber, String panNumber, String aadharNumber, String accountType, String city, String state, String pinCode) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public byte[] getPanCardpdf() {
        return panCardpdf;
    }

    public void setPanCardpdf(byte[] panCardpdf) {
        this.panCardpdf = panCardpdf;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        if (panCard.length() > 9) {
            this.panCard = panCard;
        } else {
            throw new RuntimeException("Your PAN Card Number Not matched to length !!");
        }
    }

    public boolean isDeleteCustomer() {
        return deleteCustomer;
    }

    public void setDeleteCustomer(boolean deleteCustomer) {
        this.deleteCustomer = deleteCustomer;
    }

    public String getAdharcard() {
        return adharcard;
    }

    public void setAdharcard(String adharcard) {
        this.adharcard = adharcard;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Addresses> addresses) {
        this.addresses = addresses;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", panCard='" + panCard + '\'' +
                ", deleteCustomer=" + deleteCustomer +
                ", adharcard='" + adharcard + '\'' +
                ", emailId='" + emailId + '\'' +
                ", account=" + account +
                ", addresses=" + addresses +
                ", login=" + login +
                '}';
    }
}
