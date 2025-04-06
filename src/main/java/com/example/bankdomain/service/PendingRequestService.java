package com.example.bankdomain.service;

import com.example.bankdomain.entity.BranchPendingRequest;

import java.util.Map;

public interface PendingRequestService {
//    public String managerApprove(Long id);

//    public String branchApprove(Long id);
    public Map<String ,String> branchApprove(Long id);
    public Map<String, String> managerApprove(Long id);

//    public BranchPendingRequest newPendingRequest(ManagerPendingRequest managerPendingRequest);

    public BranchPendingRequest newCustomerRequest(BranchPendingRequest branchPendingRequest);

    String branchRejectRequest(Long id, String remark);
    String managerRejectRequest(Long id, String remark);

   // public Map<String, String> branchApprove(Long id);

   // Map<String, String> managerApprove(Long id);


}
