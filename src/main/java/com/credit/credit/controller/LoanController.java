package com.credit.credit.controller;

import com.credit.credit.model.LoanDTO;
import com.credit.credit.service.loan.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @GetMapping(value = "/{tckn}/{birthDate}")
    public LoanDTO getLoanInfo(@PathVariable String tckn, @PathVariable String birthDate) {
        return loanService.getLoanByTcknAndBirthdate(tckn, birthDate).toDTO();
    }

    @PostMapping(value = "/apply/{tckn}")
    public LoanDTO postLoan(@PathVariable String tckn) {
        return loanService.calculateLoanInfo(tckn).toDTO();
    }
}
