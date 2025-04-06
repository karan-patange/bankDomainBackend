package com.example.bankdomain.repository;

import com.example.bankdomain.entity.BranchPendingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchPendingReqRepo extends JpaRepository<BranchPendingRequest,Long> {


    List<BranchPendingRequest> findAllByStatus(BranchPendingRequest.Status status);




}
