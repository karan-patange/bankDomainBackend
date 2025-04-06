package com.example.bankdomain.repository;

import com.example.bankdomain.entity.ManagerPendingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ManagerRequestRepository extends JpaRepository<ManagerPendingRequest,Long> {
//    List<PendingRequest> findByStatus(String status);
    List<ManagerPendingRequest> findAllByStatus(ManagerPendingRequest.Status status);



}
