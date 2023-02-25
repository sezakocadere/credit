package com.credit.credit.repository;

import com.credit.credit.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findFirstByCustomerTcknAndCustomerBirthDateOrderByApplyDateDesc(String tckn, String birthDate);
}
