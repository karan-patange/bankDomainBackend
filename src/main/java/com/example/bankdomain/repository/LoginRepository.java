package com.example.bankdomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankdomain.entity.Login;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login,Long> {
    Optional<Login> findByAccountNumber(String accountNumber);

}
