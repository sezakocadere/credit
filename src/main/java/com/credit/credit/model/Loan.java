package com.credit.credit.model;

import com.credit.credit.enums.LoanStatus;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data
@Audited
public class Loan {
    @Id
    @GeneratedValue
    private Long id;
    private LoanStatus loanStatus;
    private int loanScore;
    private BigDecimal loanLimit;
    private String applyDate;
    @ManyToOne
    private Customer customer;

    public LoanDTO toDTO() {
        return new ModelMapper().map(this, LoanDTO.class);
    }
}
