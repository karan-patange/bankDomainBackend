package com.example.bankdomain.serviceimple;

import com.example.bankdomain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(String toEmail, String customerName,String accountNumber,String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("infinitybankproject@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Bank account created");
        message.setText("Dear " + customerName + ",\n\nThank you for registering with us.\n\n Your account number is: "+ accountNumber+ "\n Password: "+password+" \n\n NOTE: password is valid for 7 days, plz login and chnage your password \n\n Best Regards,\n Infinity bank of India ");

        mailSender.send(message);


    }



    @Override
    public void sendPendingEmail(String toEmail,String customerName){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("infinitybankproject@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Bank account created");
        message.setText("Dear, " + customerName + "\n Your Registration is Succesfully Completed and your request is sent to branch for approval." +
                " \n after Approvel you will get your account number and password \n\n\n " +
                "Thank You ");

        mailSender.send(message);

    }

    @Override
    public void sendRejectByBranch(String toEmail, String customerName, String remark) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("infinitybankproject@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Account Request Rejected by Branch");
        message.setText("Dear, " + customerName + "\n your request for Account Creation is Rejected by Branch ." +
                " \n with following Remark " + remark +
                "Thank You ");

        mailSender.send(message);
    }

    @Override
    public void sendRejectByManager(String toEmail, String customerName, String remark) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("infinitybankproject@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Account Request Rejected by Branch");
        message.setText("Dear, " + customerName + "\n your request for Account Creation is Rejected at Manager Level ." +
                " \n with following Remark " + remark +
                "Thank You ");

        mailSender.send(message);
    }

}
