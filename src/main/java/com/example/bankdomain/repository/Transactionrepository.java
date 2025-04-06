package com.example.bankdomain.repository;


import com.example.bankdomain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Transactionrepository extends JpaRepository<Transaction,Long> {



    List<Transaction> findByAccountNumber(String accountNumber);




//    @Query("SELECT t FROM Transaction t WHERE t.account.accNo = :accNo AND t.time BETWEEN :startDate AND :endDate");


//    @Query("SELECT t FROM Transaction t WHERE t.account.accNo = :accNo AND CAST(t.time AS DATE) BETWEEN :startDate AND :endDate")
//    List<Transaction> statementByDateS(
//            @Param("accNo") String accountNumber,
//            @Param("startDate") Date startDate,
//            @Param("endDate") Date endDate
//    );
}
