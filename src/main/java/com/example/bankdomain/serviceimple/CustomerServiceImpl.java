package com.example.bankdomain.serviceimple;

import com.example.bankdomain.constants.BankName;
import com.example.bankdomain.entity.*;
import com.example.bankdomain.repository.*;
import com.example.bankdomain.service.CustomerService;
import com.example.bankdomain.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService  {
    @Autowired
    CustomerRepositry costomerRepository;


    @Autowired
    static BankName bankName;


   // private static final String BANK_INITIALS = bankName.bankname;

    String BANK_INITIALS = "SBI";


    public CustomerServiceImpl(CustomerRepositry costomerRepository, LoginRepository loginRepository) {
        this.costomerRepository = costomerRepository;
        this.loginRepository = loginRepository;
    }



    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        // Generate account number in the format: "KBI##########"

        String accountNumber = BANK_INITIALS + customer.getPanCard() + customer.getCustomerName().substring(1,3);
        String password = generateRandomPassword(8);


        customer.getAccount().setAccNo(accountNumber.toUpperCase());

        Customer savedCustomer = costomerRepository.save(customer);
        if (customer.getLogin() == null) {
            Login login = new Login();

            customer.setLogin(login);
        }

        customer.getLogin().setAccountNumber(accountNumber);
        customer.getLogin().setPassWord(password);
        emailService.sendWelcomeEmail(savedCustomer.getEmailId(), savedCustomer.getCustomerName(),savedCustomer.getAccount().getAccNo(),savedCustomer.getLogin().getPassWord());
       return savedCustomer;

    }




    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }









    @Override
    public List<Customer> getAllCustomers() {

        return costomerRepository.findAll();
    }

    @Override
    public  Customer getById(Long id) {
        return costomerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Customer not found with ID :"+id));
}

    @Override
    public void deleteById(Long id) {
        if (!costomerRepository.existsById(id)){
            throw new RuntimeException("id "+ id +" is not present ..!!");

        }costomerRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        List a = costomerRepository.findAll();
        if(a.size()<1){
            throw new RuntimeException("No Record Available to delete");
        }costomerRepository.deleteAll();


    }

    @Override
    public Customer softDelete(Long id) {
        Optional <Customer> selectedRow = costomerRepository.findById(id);
        Customer customer = selectedRow.get();
        customer.setDeleteCustomer(true);
        return costomerRepository.save(customer);
    }

    @Override
    public List<Customer> getOnlyNotDeleted() {
        List <Customer> allCustomers = costomerRepository.findAll();
       List <Customer> notDeleted = allCustomers
               .stream().filter(c->!c.isDeleteCustomer()).toList();
       

        return notDeleted;
    }

    @Override
    public List<Customer> getAllCustomersSorted() {
        List<Customer> allCustomer = costomerRepository.findAll();
        List<Customer> sortedCustomers = allCustomer.stream()
                .sorted((c1,c2)->c1.getCustomerName()
                        .compareToIgnoreCase(c2.getCustomerName()))
                .collect(Collectors.toList());
        return sortedCustomers;
    }

    @Override
    public ResponseEntity<Customer> updateCustomer(Long id, Customer c) {
        Optional<Customer> existCustomer = costomerRepository.findById(id);
        if(existCustomer.isPresent()){
            Customer customer = existCustomer.get();
            customer.setCustomerName(c.getCustomerName());
            customer.setMobileNumber(c.getMobileNumber());
            Customer saved = costomerRepository.save(customer);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
}

@Autowired
    StatementRepository statementRepository;

    @Override
    public Transaction depositeAmount(Transaction transaction ) {

        Transaction t=  transactionRepository.save(transaction);
        return t;
    }





    @Autowired
     AccountRepository accountRepository;

    @Autowired
     Transactionrepository transactionRepository;

    @Transactional
    public String depositrAmountByID(Long id, String depositeMode, Double amount, String depositeDetails) {

        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent()) {
            return "User not found";
        }
        Account account = accountOptional.get();
        Transaction transaction = new Transaction();
        transaction.setdepositeMode(depositeMode);
        transaction.setAmount(amount);
        transaction.setAccountNumber(account.getAccNo());
        transaction.setDepositeDetails(depositeDetails);
        transaction.setAccount(account);


        account.setBalance(account.getBalance()+amount);
        transactionRepository.save(transaction);
        accountRepository.save(account);

        Statement statement = new Statement();
        statement.setAmount(amount);
        statement.setTime(transaction.getTime());
        statement.setAccountNumber(account.getAccNo());
        statement.setTransactionType("DEPOSITE");
        statementRepository.save(statement);

        return "Transaction successful, "+ amount + " Succesfully deposited in " + account.getAccNo() + " on, "+ transaction.getTime();
    }

    @Autowired
    WithdrawalRepository withdrawalRepository;

    @Override
    public java.lang.String widrowAmount(Long accId, java.lang.String withdrawMode, java.lang.String withdrawDetails, Double amount){

        Optional<Account> accountOptional = accountRepository.findById(accId);
        if (!accountOptional.isPresent()) {
            return "User not found with id, : " + accId;
        }
        Account account = accountOptional.get();
       Withdrawal withdrawal = new Withdrawal();
        if (account.getBalance()<=100){
            return "inefficient balance for deposite, minimum balance should be 100 rupees...!!";

        }
        Double remainingBalance = account.getBalance()-amount;
        if (remainingBalance<100){
            return "inefficient balance";
        }
        withdrawal.setAmount(amount);
        withdrawal.setAccountNumber(account.getAccNo());
        withdrawal.setWithdrawMode(withdrawMode);
        withdrawal.setWithdrawDetails(withdrawDetails);
        account.setBalance(account.getBalance()-amount);
        withdrawal.setAccount(account);
        withdrawalRepository.save(withdrawal);
        accountRepository.save(account);

        Statement statement = new Statement();
        statement.setAmount(amount);
        statement.setTime(withdrawal.getTime());
        statement.setAccountNumber(account.getAccNo());
        statement.setTransactionType("WITHDRAW");
        statementRepository.save(statement);


        return "succesfully widrowed, " + amount + " from "+ account.getAccNo() +" available balance is, " + account.getBalance();
    }











    public List<Statement> accounStatement(String accountNumber) {
        return statementRepository.findByAccountNumber(accountNumber);

    }




    public List<Statement> statementByDateS(String accountNumber, Date startDate, Date endDate) {
        return statementRepository.statementByDateS(accountNumber, startDate, endDate);



    }

    @Autowired
    private LoginRepository loginRepository;





    @Autowired
    private EmailService emailService;





    @Override
    public String validateLogin(String accountNumber, String password) {
        Optional<Login> loginOptional = loginRepository.findByAccountNumber(accountNumber);

        if (loginOptional.isPresent()) {
            Login login = loginOptional.get();
            if (login.getPassWord().equals(password)) {
                return "true"; // Login successful
            } else {
                return "WRONG PASSWORD";
            }
        } else {
            return "ACCOUNT NUMBER NOT FOUND";
        }
    }





@Autowired
ManagerRequestRepository managerRequestRepository;















}











//SELECT * FROM ACCOUNT ;
//SELECT * FROM ADDRESSES ;
//SELECT * FROM  CUSTOMER ;
//SELECT * FROM TRANSACTION;



