package com.credit.credit.service;

import com.credit.credit.model.Customer;
import com.credit.credit.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomer();
    Customer createCustomer(CustomerDTO customerDTO);

    void removeCustomer(Long id);
    Customer updateCustomer(Long id, CustomerDTO customer);

}
