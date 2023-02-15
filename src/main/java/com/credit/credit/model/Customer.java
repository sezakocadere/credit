package com.credit.credit.model;

import com.credit.credit.enums.Status;
import lombok.Data;

import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Customer {
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

    public CustomerDTO toDTO() {
        return new ModelMapper().map(this, CustomerDTO.class);
    }
}
