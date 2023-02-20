package com.credit.credit.service.loan;

import com.credit.credit.enums.LoanStatus;
import com.credit.credit.enums.Status;
import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.model.Loan;
import com.credit.credit.repository.LoanRepository;
import com.credit.credit.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;


@AllArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {
    private static final BigDecimal MINIMUM_SALARY = BigDecimal.valueOf(5000);
    private static final BigDecimal MAX_SALARY = BigDecimal.valueOf(10000);
    private static final int LOAN_SCORE_LIMIT = 500;
    private static final int LOAN_SCORE_MAX = 1000;
    private static final BigDecimal LOAN_LIMIT_PARAMETER = BigDecimal.valueOf(4);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);


    private final LoanRepository loanRepository;
    private final CustomerService customerService;

    public int findLoanScore() {
        return ThreadLocalRandom.current().nextInt(400, 1000 + 1);
    }

    @Override
    public Loan getLoanByTcknAndBirthdate(String tckn, String birthDate) {
        Loan loan = loanRepository.findFirstByCustomerTcknAndCustomerBirthDateOrderByApplyDateDesc(tckn, birthDate);
        if (loan == null) {
            throw new NotFoundObject("Customer / Loan  Not Found");
        }

        return loan;
    }

    @Override
    public Loan calculateLoanInfo(String tckn) {
        Customer customer = findCustomer(tckn);
        Loan loan = new Loan();
        loan.setCustomer(customer);
        int loanScore = findLoanScore();
        loan.setLoanScore(loanScore);
        loan.setApplyDate(String.valueOf(OffsetDateTime.now()));

        BigDecimal salary = customer.getSalary();
        if (loanScore < LOAN_SCORE_LIMIT) {
            loan.setLoanStatus(LoanStatus.DENIED);
            loanRepository.save(loan);
            return loan;
        } else if (LOAN_SCORE_LIMIT < loanScore && loanScore < LOAN_SCORE_MAX) {
            if (salary.compareTo(MINIMUM_SALARY) < 0) {
                loan.setLoanLimit(customer.isGuarantee() ? customer.getGuaranteeAmount().multiply(BigDecimal.valueOf(10))
                        .divide(ONE_HUNDRED).add(MAX_SALARY) : MAX_SALARY);
            } else if (MINIMUM_SALARY.compareTo(salary) < 0 && salary.compareTo(MAX_SALARY) < 0) {
                loan.setLoanLimit(customer.isGuarantee() ? customer.getGuaranteeAmount().multiply(BigDecimal.valueOf(20))
                        .divide(ONE_HUNDRED).add(BigDecimal.valueOf(20000)) : BigDecimal.valueOf(20000));
            } else if (salary.compareTo(MAX_SALARY) > 0) {
                BigDecimal limit = salary.multiply(LOAN_LIMIT_PARAMETER).divide(BigDecimal.valueOf(2));
                loan.setLoanLimit(customer.isGuarantee() ? customer.getGuaranteeAmount().multiply(BigDecimal.valueOf(25))
                        .divide(ONE_HUNDRED).add(limit) : limit);
            }

        } else {
            BigDecimal limit = salary.multiply(LOAN_LIMIT_PARAMETER);
            loan.setLoanLimit(customer.isGuarantee() ? customer.getGuaranteeAmount().multiply(BigDecimal.valueOf(50))
                    .divide(ONE_HUNDRED).add(limit) : limit);
        }
        loan.setLoanStatus(LoanStatus.APPROVED);
        loanRepository.save(loan);
        return loan;
    }

    Customer findCustomer(String tckn) {
        Customer customer = customerService.getCustomerByTckn(tckn);
        if (customer == null || customer.getStatus().equals(Status.PASSIVE)) {
            throw new NotFoundObject("Customer Not Found");
        }
        return customer;
    }
}