package com.example.bankdomain.service;

public interface EmailService {
    public void sendWelcomeEmail(String toEmail, String customerName,String accountNumber,String password);
    public void sendPendingEmail(String toEmail,String customerName);

    public void sendRejectByBranch(String toEmail,String customerName,String remark);

    public void sendRejectByManager(String toEmail,String customerName,String remark);



}
