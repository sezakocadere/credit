package com.credit.credit;

import com.credit.credit.enums.Status;
import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.model.RequestCustomerDTO;
import com.credit.credit.repository.CustomerRepository;
import com.credit.credit.service.customer.CustomerService;
import com.credit.credit.service.customer.CustomerServiceImpl;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerServiceImplTest {
    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Rule
    public final ExpectedException exp = ExpectedException.none();

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerService customerService;

    @Before //run before each test
    public void setup() {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void getAllCustomers() {
        // Arrange
        Customer customer = createCustomer();
        List<Customer> customers = List.of(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<Customer> responseCustomers = customerService.getAllCustomer();

        // Assert
        softly.assertThat(responseCustomers.size()).isEqualTo(customers.size());
        softly.assertThat(responseCustomers.get(0).getId()).isEqualTo(customer.getId());

    }

    @Test
    public void getCustomer() {
        // Arrange
        Customer customer = createCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        Customer responseCustomer = customerService.getCustomerById(customer.getId());

        // Arrange
        softly.assertThat(responseCustomer.getId()).isEqualTo(customer.getId());
    }

    @Test
    public void getCustomerByInvalidValue() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        exp.expect(NotFoundObject.class);

        customerService.getCustomerById(1L);
    }

    @Test
    public void getCustomerByTcknAndStatus() {
        // Arrange
        Customer customer = createCustomer();
        when(customerRepository.findByTckn(customer.getTckn())).thenReturn(Optional.of(customer));

        // Act
        Customer responseCustomer = customerService.getCustomerByTcknAndStatus(customer.getTckn(), Status.ACTIVE);

        // Assert
        softly.assertThat(responseCustomer.getId()).isEqualTo(customer.getId());
    }

    @Test
    public void getCustomerByTcknByInvalidValue() {
        when(customerRepository.findByTckn("12345678901")).thenThrow(new NotFoundObject("Customer Not Found"));
        exp.expect(NotFoundObject.class);

        customerService.getCustomerByTcknAndStatus("12345678901", Status.ACTIVE);
    }

    @Test
    public void postCustomer() {
        // Arrange
        RequestCustomerDTO requestDTO = new RequestCustomerDTO();
        requestDTO.setName("Leman");

        // Act
        Customer responseCustomer = customerService.createCustomer(requestDTO);

        // Assert
        softly.assertThat(responseCustomer.getName()).isEqualTo(requestDTO.getName());
    }

    @Test
    public void removeCustomer() {
        // Arrange
        Customer customer = createCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        customerService.removeCustomer(customer.getId());

        // Assert
        softly.assertThat(customer.getStatus()).isEqualTo(Status.PASSIVE);
    }

    @Test
    public void removeCustomerByInvalidValue() {
        // Arrange
        when(customerRepository.findById(7L)).thenReturn(Optional.empty());
        exp.expect(NotFoundObject.class);

        // Act
        customerService.removeCustomer(7L);
    }

    @Test
    public void updateCustomer() {
        // Arrange
        RequestCustomerDTO updateDTO = new RequestCustomerDTO();
        updateDTO.setName("Seza");

        Customer customer = createCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        Customer responseCustomer = customerService.updateCustomer(customer.getId(), updateDTO);

        // Assert
        softly.assertThat(responseCustomer.getName()).isEqualTo(customer.getName());
    }

    @Test
    public void updateCustomerByInvalidValue() {
        // Arrange
        RequestCustomerDTO updateDTO = new RequestCustomerDTO();
        updateDTO.setName("Seza");
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        exp.expect(NotFoundObject.class);

        // Act
        customerService.updateCustomer(2L, updateDTO);
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(123L);
        customer.setName("Seza");
        customer.setSurname("Kocadere");
        customer.setTckn("12345678901");
        customer.setBirthDate("1996-07-02");
        customer.setSalary(BigDecimal.valueOf(12500));
        customer.setStatus(Status.ACTIVE);
        return customer;
    }
}