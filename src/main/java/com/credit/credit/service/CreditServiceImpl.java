package com.credit.credit.service;

import com.credit.credit.model.Credit;
import com.credit.credit.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {
    private final CustomerRepository customerRepository;

    @Override
    public Credit getByTcknAndBirthdate(String tckn, Date birthDate) {
      return null;
    }

    @Override
    public int calculateCreditScore(Credit credit) {
        return 0;
    }
}
