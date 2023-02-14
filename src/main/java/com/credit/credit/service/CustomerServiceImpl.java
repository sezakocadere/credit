package com.credit.credit.service;

import com.credit.credit.enums.Status;
import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.model.CustomerDTO;
import com.credit.credit.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer getCustomerById(Long id) {
        return getCustomer(id);
    }

    @Transactional
    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setStatus(Status.ACTIVE);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    @Override
    public void removeCustomer(Long id) {
        //add credit control
        Customer customer = getCustomer(id);
        customer.setStatus(Status.PASSIVE);
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = getCustomer(id);
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customer.setTckn(customerDTO.getTckn());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setSalary(customerDTO.getSalary());
        customer.setGuaranteeAmount(customerDTO.getGuaranteeAmount());
        if (customer.getGuaranteeAmount().compareTo(BigDecimal.ZERO) > 0) {
            customer.setGuarantee(true);
        }
        return customer;
    }

    private Customer getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundObject("Customer Not Found"));
        return customer;
    }
}