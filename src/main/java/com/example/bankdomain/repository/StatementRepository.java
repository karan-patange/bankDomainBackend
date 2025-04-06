package com.example.bankdomain.repository;


import com.example.bankdomain.entity.Statement;
import com.example.bankdomain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface StatementRepository extends JpaRepository<Statement,Long> {

    List<Statement> findByAccountNumber(String accountNumber);


    @Query("SELECT s FROM Statement s WHERE s.account.accNo = :accNo AND CAST(s.time AS DATE) BETWEEN :startDate AND :endDate")
    List<Statement> statementByDateS(
            @Param("accNo") String accountNumber,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
