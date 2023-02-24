package com.credit.credit.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Validated
@Data
public class RequestCustomerDTO {
    private String name;
    private String surname;
    @Size(min = 11, max = 11, message = "Tckn size must be 11")
    @NotNull(message = "Tckn may not be null")
    private String tckn;
    private String phoneNumber;
    private String birthDate;
    private BigDecimal salary;
    private boolean guarantee;
    private BigDecimal guaranteeAmount;
}
