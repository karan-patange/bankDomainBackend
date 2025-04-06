package com.example.bankdomain.repository;

import com.example.bankdomain.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
//Component
public interface CustomerRepositry extends JpaRepository<Customer,Long> {

    @Transactional
    @Modifying
    @Query("update Customer c set c.deleteCustomer=true where c.id=:custId ")
    // public void softDeleteById(@Param("custId") Long id);

//@Query("update Customer_Registration c set c.delete_Flage=true where c.custm_id=:custId nativeQuery = true")
// public void softDeleteById(@Param("custId") Long id);
    public void softDeleteById(Long custId);

    boolean existsByAdharcard(String adharcard);


}

