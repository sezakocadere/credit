package com.credit.credit.model;

import com.credit.credit.enums.Status;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Data
@Audited
public class CustomerDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    @Size(min = 11, max = 11, message = "Tckn size must be 11")
    @NotNull
    private String tckn;
    private String phoneNumber;
    private String birthDate;
    private BigDecimal salary;
    private Status status;
    private boolean guarantee;
    private BigDecimal guaranteeAmount;
}
