package com.example.bankdomain.service;

import com.example.bankdomain.entity.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CustomerService {


    public Customer saveCustomer(Customer customer);

    public Transaction depositeAmount(Transaction transaction);

    public List<Customer> getAllCustomers();

    public Customer getById(Long id);

    public void deleteById (Long id);

    public void deleteAll();

    public Customer softDelete(Long id);

    public List<Customer> getOnlyNotDeleted();

    public List<Customer> getAllCustomersSorted();

    public ResponseEntity<Customer> updateCustomer(Long id, Customer c);

    public String depositrAmountByID(Long accId, String upiId, Double amount,String depositeDetails);

    public  String widrowAmount(Long accId,String withdrawMode,String withdrawDetails,Double amount);

    public List<Statement> accounStatement(String accountNumber);


    String validateLogin(String accountNumber, String password);




public String changePassword(String accountNumber,String password);

}
