package com.credit.credit.repository;

import com.credit.credit.model.Customer;
import com.credit.credit.model.RequestCustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
   // boolean existsByTckn(String tckn);

    Customer findByTckn(String tckn);
}