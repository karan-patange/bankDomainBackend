package com.example.bankdomain.controller;


import com.example.bankdomain.dto.RejectionRequest;
import com.example.bankdomain.entity.*;
import com.example.bankdomain.repository.BranchPendingReqRepo;
import com.example.bankdomain.repository.CustomerRepositry;
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
//@Controller
//@ResponseBody
@RequestMapping("api/bank")
//@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepositry customerRepository;



    @GetMapping("/getallcustomers")
    public List<Customer> getAllCustomers(){

//        List<Customer> all = customerRepository.findAll();
//        List<Customer> startWithP = all.stream()
//                .filter(c -> c.getpanCard().startsWith(""))
//                .collect(Collectors.toList());
        return customerService.getAllCustomers();
      //  return startWithP;
    }

    @GetMapping("/getbyid/{id}")
    public Customer getCustomerById(@PathVariable("id") Long customid){
        System.out.println("coustomer id : "+customid);
        return customerService.getById(customid);
    }

    @GetMapping("/getbyid")
    public Customer getCustomerById2(@RequestParam("id") Long customid) {
        System.out.println("customer id : " + customid);
        return customerService.getById(customid);
    }


    @DeleteMapping("/deletbyid/{id}")
    public String deleteById(@PathVariable long id){
         customerService.deleteById(id);
        return "Customer Deleted Sccesfully with id : "+ id;

    }
     @DeleteMapping("/deleteAll")
    public String deleteAll(){
        customerService.deleteAll();
        return "All record Deleted Succesfully...!!";
    }
@PutMapping("/flagDeleted/{id}")
    public String flagDeleted(@PathVariable Long id){
        customerService.softDelete(id);
        return "customer with id "+ id + " is flaged as deleted";
}
@GetMapping("/getNotDeleted")
public List<Customer> notDeleted(){
      return   customerService.getOnlyNotDeleted();

}

@GetMapping("/getSortedCustomers")
    public List<Customer> sortedCustomers(){
        return customerService.getAllCustomersSorted();
}

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestParam  Long id,@RequestBody Customer c){

        ResponseEntity<Customer> dataUpdate = customerService.updateCustomer(id, c);
        return ResponseEntity.ok(dataUpdate.getBody());
}


    @PostMapping("/customerss")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
//        if (customer.getAccount() != null) {
//            customer.getAccount().setCustomer(customer);
//        }

        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PostMapping("/saveWithNum")
    public Customer saveacc(@RequestBody Customer customer){

        Customer saveacc = customerService.saveCustomer(customer);
        //emailService.sendWelcomeEmail(saveacc.getEmailId(), saveacc.getCustomerName(),saveacc.getLogin().getAccountNumber(),saveacc.getLogin().getPassWord());
        return saveacc;

    }



//Long id, String depositeMode, Double amount, String depositeDetails

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam Long id,
                                          @RequestParam String depositeMode,
                                          @RequestParam Double amount,
                                          @RequestParam String depositeDetails) {
        String response = customerService.depositrAmountByID(id, depositeMode, amount, depositeDetails);
        return ResponseEntity.ok(response);
    }


   //  Long accId,String withdrawMode,String withdrawDetails,Double amount
    @PostMapping("/withdraw")
    public ResponseEntity<String> witdow(@RequestParam Long id,
                                          @RequestParam String withdrawMode,
                                          @RequestParam String withdrawDetails,
                                          @RequestParam Double amount) {
        String response = customerService.widrowAmount(id,withdrawMode,withdrawDetails,amount);
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
//        if (!customer.getPanCard().matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
//            return ResponseEntity.badRequest().body("Invalid PAN card format.");
//        }
        return ResponseEntity.ok("Customer registered successfully!");



    }




//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody Login request) {
//        String response = customerService.newLogin(request.getAccountNumber(), request.getPassWord());
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login) {
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
   // PendReqServiceImpl pendReqService = new PendReqServiceImpl();









    @GetMapping("/pending-requests")
    public ResponseEntity<List<ManagerPendingRequest>> getAllPendingRequests() {
        List<ManagerPendingRequest> managerPendingRequests = pendReqServiceImpl.getAllPendingRequests();
        return ResponseEntity.ok(managerPendingRequests);
    }




    @PostMapping("/newCustomer")
    public ResponseEntity<BranchPendingRequest> createPendingRequest(@RequestBody BranchPendingRequest request) {
        BranchPendingRequest savedRequest = pendingRequestService.newCustomerRequest(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }


    @PutMapping("/branch/approve/{id}")
    public ResponseEntity<String> employeeApproveRequest(@PathVariable Long id) {
        String result = pendingRequestService.branchApprove(id).toString();
        if (result.contains("not found") || result.contains("already processed")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


//    @PutMapping("/branch/approve/{id}")
//    public ResponseEntity<Map<String, String>> employeeApproveRequest(@PathVariable Long id) {
//        Map<String, String> result = pendingRequestService.branchApprove(id);
//        if (result.get("message").contains("not found") || result.get("message").contains("already processed")) {
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


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

//    @PutMapping("/manager/approve/{id}")
//    public ResponseEntity<Map<String, String>> adminApproveRequest(@PathVariable Long id) {
//        Map<String, String> result = pendingRequestService.managerApprove(id);
//        if (result.get("message").contains("not found") || result.get("message").contains("already processed") || result.get("message").contains("failed")) {
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }





    // Updated API for Admin Rejection
    @PostMapping("/manager/reject/{id}")
    public ResponseEntity<String> adminRejectRequest(@PathVariable Long id, @RequestBody RejectionRequest rejectionRequest) {
        String remark = rejectionRequest.getRemark();

        String result = pendingRequestService.managerRejectRequest(id, remark);
        if (result.contains("not found") || result.contains("already processed")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




    //FOR GETTING ALL PENDING REQUEST FOR BRANCH

    @GetMapping("/branch/pending")
    public ResponseEntity<List<BranchPendingRequest>> getAllPendingRequestsForBranch() {
        List<BranchPendingRequest> pendingRequests = pendReqServiceImpl.getAllPendingRequestsForBranch();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

//all APPROVED FOR BRANCH

    @GetMapping("/branch/approved")
    public ResponseEntity<List<BranchPendingRequest>> getAllApprovedRequestsForBranch() {
        List<BranchPendingRequest> pendingRequests = pendReqServiceImpl.getAllApprovedRequestsForBranch();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

//ALL REJECTED BY BRANCH

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





@PostMapping("/newCustomerRequest")
public ResponseEntity<BranchPendingRequest> createPendingRequest(
            @RequestPart("request") BranchPendingRequest request,
            @RequestPart("customerPhoto") MultipartFile customerPhoto,
            @RequestPart("signaturePhoto") MultipartFile signaturePhoto,
            @RequestPart("aadharCard") MultipartFile aadharCard,
            @RequestPart("panCard") MultipartFile panCard) throws IOException {

        // Validate file types
        if (!isImageFile(customerPhoto)) {
            throw new IllegalArgumentException("Customer photo must be in JPG or PNG format");
        }
        if (!isImageFile(signaturePhoto)) {
            throw new IllegalArgumentException("Signature photo must be in JPG or PNG format");
        }
        if (!isPdfFile(aadharCard)) {
            throw new IllegalArgumentException("Aadhaar card must be in PDF format");
        }
        if (!isPdfFile(panCard)) {
            throw new IllegalArgumentException("PAN card must be in PDF format");
        }

        // Set files in the request
        request.setCustomerPhoto(customerPhoto.getBytes());
        request.setSignaturePhoto(signaturePhoto.getBytes());
        request.setAadharCard(aadharCard.getBytes());
        request.setPanCard(panCard.getBytes());

        BranchPendingRequest savedRequest = pendingRequestService.newCustomerRequest(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    // Helper methods to validate file types
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
    }

    private boolean isPdfFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("application/pdf");
    }




@Autowired
    BranchPendingReqRepo branchPendingReqRepo;


    @GetMapping("/branch/request/{id}/documents")
    public ResponseEntity<Map<String, byte[]>> getRequestDocumentsForBranch(@PathVariable Long id) {
        Optional<BranchPendingRequest> requestOpt = branchPendingReqRepo.findById(id);
        if (!requestOpt.isPresent()) {
            throw new IllegalArgumentException("Request not found!");
        }

        BranchPendingRequest request = requestOpt.get();
        Map<String, byte[]> documents = new HashMap<>();
        documents.put("customerPhoto", request.getCustomerPhoto());
        documents.put("signaturePhoto", request.getSignaturePhoto());
        documents.put("aadharCard", request.getAadharCard());
        documents.put("panCard", request.getPanCard());

        return new ResponseEntity<>(documents, HttpStatus.OK);
    }


@Autowired
    ManagerRequestRepository managerRequestRepository;

    @GetMapping("/manager/request/{id}/documents")
    public ResponseEntity<Map<String, byte[]>> getRequestDocumentsForManager(@PathVariable Long id) {
        Optional<ManagerPendingRequest> requestOpt = managerRequestRepository.findById(id);
        if (!requestOpt.isPresent()) {
            throw new IllegalArgumentException("Request not found!");
        }

        ManagerPendingRequest request = requestOpt.get();
        Map<String, byte[]> documents = new HashMap<>();
        documents.put("customerPhoto", request.getCustomerPhoto());
        documents.put("signaturePhoto", request.getSignaturePhoto());
        documents.put("aadharCard", request.getAadharCard());
        documents.put("panCard", request.getPanCard());

        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
    }

