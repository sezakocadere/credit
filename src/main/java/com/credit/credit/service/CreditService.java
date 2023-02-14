package com.credit.credit.service;

import com.credit.credit.model.Credit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface CreditService {
    Credit getByTcknAndBirthdate(@NotNull String tckn, @NotNull @JsonFormat(pattern = "dd/MM/yyyy") Date birthDate);
    int calculateCreditScore(Credit credit);
}
