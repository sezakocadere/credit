package com.credit.credit.service.customer;

import com.credit.credit.model.Customer;
import com.credit.credit.model.RequestCustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomer();
    Customer createCustomer(RequestCustomerDTO customerDTO);

    void removeCustomer(Long id);
    Customer updateCustomer(Long id, RequestCustomerDTO customer);
    //boolean checkCustomerByTckn(String tckn);

    Customer getCustomerByTckn(String tckn);
}
