package com.credit.credit.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerDTO {
    private String name;
    private String surname;
    private String tckn;
    private String phoneNumber;
    private LocalDate birthDate;
    private BigDecimal salary;
    private boolean guarantee;
    private BigDecimal guaranteeAmount;
}
