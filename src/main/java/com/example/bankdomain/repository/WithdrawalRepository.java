package com.example.bankdomain.repository;

import com.example.bankdomain.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<Withdrawal,Long> {


}
