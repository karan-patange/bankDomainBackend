package com.example.bankdomain.repository;

import com.example.bankdomain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    Optional<Account> findByAccountNumber(String accNo);

    boolean existsByAccNo(String accNo);
}
