package com.credit.credit.service.loan;

import com.credit.credit.enums.LoanStatus;
import com.credit.credit.enums.Status;
import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.model.Loan;
import com.credit.credit.repository.LoanRepository;
import com.credit.credit.service.customer.CustomerService;
import com.credit.credit.service.sms.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;


@AllArgsConstructor
@Service
@Log4j2
public class LoanServiceImpl implements LoanService {
    public static final BigDecimal LOAN_RATE_TEN = BigDecimal.valueOf(10);
    public static final BigDecimal LOAN_RATE_TWENTY = BigDecimal.valueOf(20);
    public static final BigDecimal LOAN_RATE_TWENTY_FIVE = BigDecimal.valueOf(25);
    public static final BigDecimal LOAN_RATE_FIFTY = BigDecimal.valueOf(50);
    private static final BigDecimal MINIMUM_SALARY = BigDecimal.valueOf(5000);
    private static final BigDecimal MAX_SALARY = BigDecimal.valueOf(10000);
    private static final int LOAN_SCORE_LIMIT = 500;
    private static final int LOAN_SCORE_MAX = 1000;
    private static final BigDecimal LOAN_LIMIT_PARAMETER = BigDecimal.valueOf(4);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final SmsService smsService;

    private int findLoanScore() {
        return ThreadLocalRandom.current().nextInt(400, 1000 + 1);
    }

    @Override
    public Loan getLoanByTcknAndBirthdate(String tckn, String birthDate) {
        return loanRepository.findFirstByCustomerTcknAndCustomerBirthDateOrderByApplyDateDesc(tckn, birthDate).orElseThrow((() -> new NotFoundObject("Customer / Loan Not Found")));
    }

    @Override
    public Loan calculateLoanInfo(String tckn) {
        Customer customer = findCustomer(tckn);
        int loanScore = findLoanScore();
        Loan loan = calculateAndPrepareLoan(loanScore, customer);
        loanRepository.save(loan);
        log.info("Loan is calculated! : " + loan);
        return loan;
    }

    private Loan calculateAndPrepareLoan(int loanScore, Customer customer) {
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanScore(loanScore);
        loan.setApplyDate(String.valueOf(OffsetDateTime.now()));

        if (loanScore < LOAN_SCORE_LIMIT) {
            loan.setLoanStatus(LoanStatus.DENIED);
            new Thread(smsService.sendSmsMessageByLoanStatus(LoanStatus.DENIED)).run();
            return loan;
        }
        BigDecimal salary = customer.getSalary();
        loan.setLoanLimit(loanScore < LOAN_SCORE_MAX ? calculateLoanLimit(customer, salary) :
                checkAndCalculateGuarantee(customer, salary.multiply(LOAN_LIMIT_PARAMETER), LOAN_RATE_FIFTY));
        loan.setLoanStatus(LoanStatus.APPROVED);
        new Thread(smsService.sendSmsMessageByLoanStatus(loan.getLoanStatus())).run();
        return loan;
    }

    private BigDecimal calculateLoanLimit(Customer customer, BigDecimal salary) {
        if (salary.compareTo(MINIMUM_SALARY) < 0) {
            return checkAndCalculateGuarantee(customer, MAX_SALARY, LOAN_RATE_TEN);
        } else if (salary.compareTo(MAX_SALARY) < 0) {
            return checkAndCalculateGuarantee(customer, BigDecimal.valueOf(20000), LOAN_RATE_TWENTY);
        } else {
            return checkAndCalculateGuarantee(customer, salary.multiply(LOAN_LIMIT_PARAMETER).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP), LOAN_RATE_TWENTY_FIVE);
        }
    }

    private Customer findCustomer(String tckn) {
        return customerService.getCustomerByTcknAndStatus(tckn, Status.ACTIVE);
    }

    private BigDecimal checkAndCalculateGuarantee(Customer customer, BigDecimal amount, BigDecimal rate) {
        return customer.isGuarantee() ? customer.getGuaranteeAmount().multiply(rate).divide(ONE_HUNDRED, RoundingMode.HALF_UP).add(amount) : amount;
    }
}