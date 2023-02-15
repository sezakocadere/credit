package com.credit.credit.model;

import com.credit.credit.enums.LoanStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
public class LoanDTO {
    @Id
    @GeneratedValue
    private Long id;
    private LoanStatus loanStatus;
    private int loanScore;
    private BigDecimal loanLimit;
    private OffsetDateTime applyDate;
    @ManyToOne
    private Customer customer;
}
