package com.example.bankdomain.serviceimple;

import com.example.bankdomain.entity.*;
import com.example.bankdomain.repository.BranchPendingReqRepo;
import com.example.bankdomain.repository.CustomerRepositry;
import com.example.bankdomain.repository.ManagerRequestRepository;
import com.example.bankdomain.service.CustomerService;
import com.example.bankdomain.service.EmailService;
import com.example.bankdomain.service.PendingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PendReqServiceImpl implements PendingRequestService {

    @Autowired
    private ManagerRequestRepository managerRequestRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepositry customerRepositry;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerServiceImpl customerService1;

    @Autowired
    private BranchPendingReqRepo branchPendingReqRepo;


    public List<ManagerPendingRequest> getAllPendingRequests() {
        return managerRequestRepository.findAllByStatus(ManagerPendingRequest.Status.PENDING);
    }

    @Override
    public BranchPendingRequest newCustomerRequest(BranchPendingRequest branchPendingRequest) {
        return branchPendingReqRepo.save(branchPendingRequest);
    }

    @Override
    public String branchRejectRequest(Long id, String remark) {
        Optional<BranchPendingRequest> requestOpt = branchPendingReqRepo.findById(id);
        if (!requestOpt.isPresent()) {
            return "Pending request not found!";
        }

        BranchPendingRequest branchPendingRequest = requestOpt.get();
        if (branchPendingRequest.getStatus() != BranchPendingRequest.Status.PENDING) {
            return "Request already processed!";
        }

        branchPendingRequest.setStatus(BranchPendingRequest.Status.REJECTED);
        branchPendingRequest.setRemark(remark);
        branchPendingReqRepo.save(branchPendingRequest);
        emailService.sendRejectByBranch(branchPendingRequest.getEmailId(), branchPendingRequest.getName(), branchPendingRequest.getRemark());
        return "Request rejected by employee with remark: " + remark;
    }

    @Override
    public String managerRejectRequest(Long id, String remark) {
        Optional<ManagerPendingRequest> requestOpt = managerRequestRepository.findById(id);
        if (!requestOpt.isPresent()) {
            return "Pending request not found!";
        }

        ManagerPendingRequest managerPendingRequest = requestOpt.get();
        if (managerPendingRequest.getStatus() != ManagerPendingRequest.Status.PENDING) {
            return "Request already processed!";
        }

        managerPendingRequest.setStatus(ManagerPendingRequest.Status.REJECTED);
        managerPendingRequest.setRemark(remark);
        managerRequestRepository.save(managerPendingRequest);
        return "Request rejected by admin with remark: " + remark;
    }

    public List<BranchPendingRequest> getAllPendingRequestsForBranch() {
        return branchPendingReqRepo.findAllByStatus(BranchPendingRequest.Status.PENDING);
    }

    public List<BranchPendingRequest> getAllApprovedRequestsForBranch() {
        return branchPendingReqRepo.findAllByStatus(BranchPendingRequest.Status.APPROVED);
    }

    public List<BranchPendingRequest> getAllRejectedRequestsForBranch() {
        return branchPendingReqRepo.findAllByStatus(BranchPendingRequest.Status.REJECTED);
    }

    public List<ManagerPendingRequest> getAllPendingRequestsForManager() {
        return managerRequestRepository.findAllByStatus(ManagerPendingRequest.Status.PENDING);
    }

    public List<ManagerPendingRequest> getAllApprovedRequestsForManager() {
        return managerRequestRepository.findAllByStatus(ManagerPendingRequest.Status.APPROVED);
    }

    public List<ManagerPendingRequest> getAllRejectedRequestsForManager() {
        return managerRequestRepository.findAllByStatus(ManagerPendingRequest.Status.REJECTED);
    }

    @Override
    public Map<String, String> branchApprove(Long id) {
        Optional<BranchPendingRequest> requestOpt = branchPendingReqRepo.findById(id);
        if (!requestOpt.isPresent()) {
            return Map.of("message", "Pending request not found!");
        }

        BranchPendingRequest empRequest = requestOpt.get();
        if (empRequest.getStatus() != BranchPendingRequest.Status.PENDING) {
            return Map.of("message", "Request already processed!");
        }

        ManagerPendingRequest managerPendingRequest = new ManagerPendingRequest();
        managerPendingRequest.setName(empRequest.getName());
        managerPendingRequest.setMobileNumber(empRequest.getMobileNumber());
        managerPendingRequest.setPanNumber(empRequest.getPanNumber());
        managerPendingRequest.setAadharNumber(empRequest.getAadharNumber());
        managerPendingRequest.setEmailId(empRequest.getEmailId());
        managerPendingRequest.setAccountType(empRequest.getAccountType());
        managerPendingRequest.setCity(empRequest.getCity());
        managerPendingRequest.setState(empRequest.getState());
        managerPendingRequest.setPinCode(empRequest.getPinCode());
        managerPendingRequest.setStatus(ManagerPendingRequest.Status.PENDING);
        managerPendingRequest.setCustomerPhoto(empRequest.getCustomerPhoto());
        managerPendingRequest.setSignaturePhoto(empRequest.getSignaturePhoto());
        managerPendingRequest.setAadharCard(empRequest.getAadharCard());
        managerPendingRequest.setPanCard(empRequest.getPanCard());

        empRequest.setStatus(BranchPendingRequest.Status.APPROVED);
        managerRequestRepository.save(managerPendingRequest);

        return Map.of("message", "Request approved by employee, sent to admin.");
    }

    @Override
    public Map<String, String> managerApprove(Long id) {
        Optional<ManagerPendingRequest> requestOpt = managerRequestRepository.findById(id);
        if (!requestOpt.isPresent()) {
            return Map.of("message", "Pending request not found!");
        }

        ManagerPendingRequest managerRequest = requestOpt.get();
        if (managerRequest.getStatus() != ManagerPendingRequest.Status.PENDING) {
            return Map.of("message", "Request already processed!");
        }

        Customer customer = new Customer();
        customer.setCustomerName(managerRequest.getName());
        customer.setMobileNumber(managerRequest.getMobileNumber());
        customer.setPanCard(managerRequest.getPanNumber());
        customer.setAdharcard(managerRequest.getAadharNumber());
        customer.setEmailId(managerRequest.getEmailId());
        customer.setCustomerPhoto(managerRequest.getCustomerPhoto());
        customer.setSignaturePhoto(managerRequest.getSignaturePhoto());
        customer.setAadharCard(managerRequest.getAadharCard());
        customer.setPanCardpdf(managerRequest.getPanCard());

        String accountNumber = "IBI" + UUID.randomUUID().toString().substring(0, 8);
        Account account = new Account();
        account.setAccType(managerRequest.getAccountType());
        account.setBalance(0.0);
        account.setAccNo(accountNumber);
        customer.setAccount(account);

        Addresses address = new Addresses();
        address.setCity(managerRequest.getCity());
        address.setState(managerRequest.getState());
        address.setPinCode(managerRequest.getPinCode());
        customer.setAddresses(List.of(address));

        String password = customerService1.generateRandomPassword(8);
        Login login = new Login();
        login.setAccountNumber(accountNumber);
        login.setPassWord(password);
        login.setRole("customer");
        login.setCustomer(customer);
        customer.setLogin(login);

        try {
            customerRepositry.save(customer);
            managerRequest.setStatus(ManagerPendingRequest.Status.APPROVED);
            managerRequestRepository.save(managerRequest);
            emailService.sendWelcomeEmail(customer.getEmailId(), customer.getCustomerName(), accountNumber, password);
            return Map.of("message", "User approved and added to Customer database.");
        } catch (Exception e) {
            return Map.of("message", "Something went wrong while saving data.");
        }
    }
}
