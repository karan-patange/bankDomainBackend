package com.example.bankdomain.controller;

import com.example.bankdomain.dto.RejectionRequest;
import com.example.bankdomain.entity.*;
import com.example.bankdomain.repository.BranchPendingReqRepo;
import com.example.bankdomain.repository.CustomerRepositry;
import com.example.bankdomain.repository.LoginRepository;
import com.example.bankdomain.repository.ManagerRequestRepository;
import com.example.bankdomain.service.CustomerService;
import com.example.bankdomain.service.EmailService;
import com.example.bankdomain.service.PendingRequestService;
import com.example.bankdomain.serviceimple.CustomerServiceImpl;
import com.example.bankdomain.serviceimple.PendReqServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/bank")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepositry customerRepository;

    @GetMapping("/getallcustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getbyid/{id}")
    public Customer getCustomerById(@PathVariable("id") Long customid) {
        return customerService.getById(customid);
    }

    @GetMapping("/getbyid")
    public Customer getCustomerById2(@RequestParam("id") Long customid) {
        return customerService.getById(customid);
    }

    @DeleteMapping("/deletbyid/{id}")
    public String deleteById(@PathVariable long id) {
        customerService.deleteById(id);
        return "Customer Deleted Successfully with id : " + id;
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        customerService.deleteAll();
        return "All records Deleted Successfully...!!";
    }

    @PutMapping("/flagDeleted/{id}")
    public String flagDeleted(@PathVariable Long id) {
        customerService.softDelete(id);
        return "customer with id " + id + " is flagged as deleted";
    }

    @GetMapping("/getNotDeleted")
    public List<Customer> notDeleted() {
        return customerService.getOnlyNotDeleted();
    }

    @GetMapping("/getSortedCustomers")
    public List<Customer> sortedCustomers() {
        return customerService.getAllCustomersSorted();
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestParam Long id, @RequestBody Customer c) {
        ResponseEntity<Customer> dataUpdate = customerService.updateCustomer(id, c);
        return ResponseEntity.ok(dataUpdate.getBody());
    }

    @PostMapping("/customerss")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PostMapping("/saveWithNum")
    public Customer saveacc(@RequestBody Customer customer) {
        Customer saveacc = customerService.saveCustomer(customer);
        return saveacc;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam Long id,
                                          @RequestParam String depositeMode,
                                          @RequestParam Double amount,
                                          @RequestParam String depositeDetails) {
        String response = customerService.depositrAmountByID(id, depositeMode, amount, depositeDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> witdow(@RequestParam Long id,
                                         @RequestParam String withdrawMode,
                                         @RequestParam String withdrawDetails,
                                         @RequestParam Double amount) {
        String response = customerService.widrowAmount(id, withdrawMode, withdrawDetails, amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statement/{accountNumber}")
    public List<Statement> getTransactionsByAccount(@PathVariable String accountNumber) {
        return customerService.accounStatement(accountNumber);
    }

    @Autowired
    CustomerServiceImpl customerServiceimpl;

    @GetMapping("/statementByDate")
    public List<Statement> statementByDateC(@RequestParam String accountNumber,
                                            @RequestParam String startDate,
                                            @RequestParam String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return customerServiceimpl.statementByDateS(accountNumber, sdf.parse(startDate), sdf.parse(endDate));
    }

    @PostMapping("/saveWithNumm")
    public ResponseEntity<?> registerCustomer2(@RequestBody Customer customer) {
        if (customer.getMobileNumber().toString().length() != 10) {
            return ResponseEntity.badRequest().body("Mobile number must be exactly 10 digits.");
        }
        return ResponseEntity.ok("Customer registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginpre(@RequestBody Login login) {
        String result = customerService.validateLogin(login.getAccountNumber(), login.getPassWord());
        if ("true".equals(result)) {
            return ResponseEntity.ok(result);
        } else if ("WRONG PASSWORD".equals(result)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @Autowired
    EmailService emailService;

    @PostMapping("/mail")
    public Customer registerCustomerm(@RequestBody Customer customer) {
        return (customer);
    }

    @Autowired
    PendingRequestService pendingRequestService;

    @Autowired
    PendReqServiceImpl pendReqServiceImpl;

    @GetMapping("/pending-requests")
    public ResponseEntity<List<ManagerPendingRequest>> getAllPendingRequests() {
        List<ManagerPendingRequest> managerPendingRequests = pendReqServiceImpl.getAllPendingRequests();
        return ResponseEntity.ok(managerPendingRequests);
    }

    @PutMapping("/branch/approve/{id}")
    public ResponseEntity<String> employeeApproveRequest(@PathVariable Long id) {
        String result = pendingRequestService.branchApprove(id).toString();
        if (result.contains("not found") || result.contains("already processed")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/branch/reject/{id}")
    public ResponseEntity<String> employeeRejectRequest(@PathVariable Long id, @RequestBody RejectionRequest rejectionRequest) {
        String remark = rejectionRequest.getRemark();
        String result = pendingRequestService.branchRejectRequest(id, remark);
        if (result.contains("not found") || result.contains("already processed")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/manager/approve/{id}")
    public ResponseEntity<String> adminApproveRequest(@PathVariable Long id) {
        String result = pendingRequestService.managerApprove(id).toString();
        if (result.contains("not found") || result.contains("already processed") || result.contains("failed")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/manager/reject/{id}")
    public ResponseEntity<String> adminRejectRequest(@PathVariable Long id, @RequestBody RejectionRequest rejectionRequest) {
        String remark = rejectionRequest.getRemark();
        String result = pendingRequestService.managerRejectRequest(id, remark);
        if (result.contains("not found") || result.contains("already processed")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/branch/pending")
    public ResponseEntity<List<BranchPendingRequest>> getAllPendingRequestsForBranch() {
        List<BranchPendingRequest> pendingRequests = pendReqServiceImpl.getAllPendingRequestsForBranch();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/branch/approved")
    public ResponseEntity<List<BranchPendingRequest>> getAllApprovedRequestsForBranch() {
        List<BranchPendingRequest> pendingRequests = pendReqServiceImpl.getAllApprovedRequestsForBranch();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/branch/rejected")
    public ResponseEntity<List<BranchPendingRequest>> getAllRejectedRequestsForBranch() {
        List<BranchPendingRequest> pendingRequests = pendReqServiceImpl.getAllRejectedRequestsForBranch();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/manager/pending")
    public ResponseEntity<List<ManagerPendingRequest>> getAllPendingRequestsForManager() {
        List<ManagerPendingRequest> pendingRequests = pendReqServiceImpl.getAllPendingRequestsForManager();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/manager/approved")
    public ResponseEntity<List<ManagerPendingRequest>> getAllApprovedRequestsForManager() {
        List<ManagerPendingRequest> pendingRequests = pendReqServiceImpl.getAllApprovedRequestsForManager();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/manager/rejected")
    public ResponseEntity<List<ManagerPendingRequest>> getAllRejectedRequestsForManager() {
        List<ManagerPendingRequest> pendingRequests = pendReqServiceImpl.getAllRejectedRequestsForManager();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }
}
