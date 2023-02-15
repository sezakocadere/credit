package com.credit.credit.model;

import com.credit.credit.enums.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class CustomerDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String tckn;
    private String phoneNumber;
    private String birthDate;
    private BigDecimal salary;
    private Status status;
    private boolean guarantee;
    private BigDecimal guaranteeAmount;
}
