package com.credit.credit.repository;

import com.credit.credit.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findFirstByCustomerTcknAndCustomerBirthDateOrderByApplyDateDesc(String tckn, String birthDate);
}
