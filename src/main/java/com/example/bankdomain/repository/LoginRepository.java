package com.example.bankdomain.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankdomain.entity.Login;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface LoginRepository extends JpaRepository<Login,Long> {


    Optional<Login> findByAccountNumber(String accountNumber);

    Optional<Login> findByaccountNumberAndPassWordAndRole(String accountNumber, String passWord, String role);

}
