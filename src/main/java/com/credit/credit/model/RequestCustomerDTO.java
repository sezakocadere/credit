package com.credit.credit.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestCustomerDTO {
    private String name;
    private String surname;
    private String tckn;
    private String phoneNumber;
    private String birthDate;
    private BigDecimal salary;
    private boolean guarantee;
    private BigDecimal guaranteeAmount;
}
