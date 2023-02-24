package com.credit.credit.service.customer;

import com.credit.credit.model.Customer;
import com.credit.credit.model.RequestCustomerDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface CustomerService {

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomer();

    Customer createCustomer(@Valid RequestCustomerDTO customerDTO);

    void removeCustomer(Long id);

    Customer updateCustomer(Long id, RequestCustomerDTO customer);

    Customer getCustomerByTckn(@NotNull String tckn);
}
