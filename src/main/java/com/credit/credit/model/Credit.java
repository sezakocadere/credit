package com.credit.credit.model;

import com.credit.credit.enums.CreditStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data
public class Credit {
    @Id
    @GeneratedValue
    private Long id;
    private CreditStatus creditStatus;
    private int creditScore;
    private BigDecimal creditLimit;
    @ManyToOne
    private Customer customer;
}
