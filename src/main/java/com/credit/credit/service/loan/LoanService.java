package com.credit.credit.service.loan;

import com.credit.credit.model.Loan;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface LoanService {
    Loan getLoanByTcknAndBirthdate(@NotNull String tckn, String birthDate);

    Loan calculateLoanInfo(String tckn);
}
