package com.credit.credit;

import com.credit.credit.enums.LoanStatus;
import com.credit.credit.enums.Status;
import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.model.Loan;
import com.credit.credit.repository.LoanRepository;
import com.credit.credit.service.customer.CustomerService;
import com.credit.credit.service.loan.LoanService;
import com.credit.credit.service.loan.LoanServiceImpl;
import com.credit.credit.service.sms.SmsService;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoanServiceImplTest {
    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Rule
    public final ExpectedException exp = ExpectedException.none();
    @Mock
    SmsService smsService;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private LoanService loanService;

    @Before
    public void setup() {
        loanService = new LoanServiceImpl(loanRepository, customerService, smsService);
    }

    @Test
    public void getLoanByTcknAndBirthdate() {
        // Arrange
        Loan loan = createLoan();
        Customer customer = loan.getCustomer();
        when(loanRepository.findFirstByCustomerTcknAndCustomerBirthDateOrderByApplyDateDesc(
                customer.getTckn(), customer.getBirthDate())).thenReturn(Optional.of(loan));
        // Act
        Loan responseLoan = loanService.getLoanByTcknAndBirthdate(customer.getTckn(), customer.getBirthDate());
        // Assert
        softly.assertThat(responseLoan.getId()).isEqualTo(loan.getId());
    }

    @Test
    public void getLoanByTcknAndBirthdateByInvalidValue() {
        // Arrange
        when(loanRepository.findFirstByCustomerTcknAndCustomerBirthDateOrderByApplyDateDesc(
                "123", "1995-05-20")).thenThrow(new NotFoundObject("Not Found"));
        exp.expect(NotFoundObject.class);

        // Act
        loanService.getLoanByTcknAndBirthdate("123", "1995-05-20");
    }

    @Test
    public void calculateLoanInfo() {
        // Arrange
        Customer customer = createCustomer();
        when(customerService.getCustomerByTcknAndStatus(customer.getTckn(), Status.ACTIVE)).thenReturn(customer);
        when(smsService.sendSmsMessageByLoanStatus(createLoan().getLoanStatus(), customer.getPhoneNumber())).thenReturn(String.valueOf(Status.ACTIVE));


        // Act
        Loan responseLoan = loanService.calculateLoanInfo(customer.getTckn());

        // Assert
        softly.assertThat(responseLoan.getLoanScore()).isNotZero();
        softly.assertThat(responseLoan.getLoanLimit()).isNotNull();
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(123L);
        customer.setName("Seza");
        customer.setSurname("Kocadere");
        customer.setTckn("12345678901");
        customer.setBirthDate("1996-07-02");
        customer.setPhoneNumber("5346571454");
        customer.setSalary(BigDecimal.valueOf(12500));
        customer.setStatus(Status.ACTIVE);
        return customer;
    }

    private Loan createLoan() {
        Loan loan = new Loan();
        loan.setCustomer(createCustomer());
        loan.setId(11L);
        loan.setLoanStatus(LoanStatus.APPROVED);
        return loan;
    }
}