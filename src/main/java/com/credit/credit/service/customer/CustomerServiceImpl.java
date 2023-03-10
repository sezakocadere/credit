package com.credit.credit.service.customer;

import com.credit.credit.enums.Status;
import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.model.RequestCustomerDTO;
import com.credit.credit.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long id) {
        Customer customer = getCustomer(id);
        if (customer.getStatus().equals(Status.PASSIVE)) {
            throw new NotFoundObject("Customer Not Found");
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public Customer createCustomer(RequestCustomerDTO customerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setStatus(Status.ACTIVE);
        if (Objects.nonNull(customer.getGuaranteeAmount()) && customer.getGuaranteeAmount().compareTo(BigDecimal.ZERO) > 0) {
            customer.setGuarantee(true);
        }
        customerRepository.save(customer);
        log.info("Customer is created!");
        return customer;
    }

    @Transactional
    @Override
    public void removeCustomer(Long id) {
        Customer customer = getCustomer(id);
        customer.setStatus(Status.PASSIVE);
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer updateCustomer(Long id, RequestCustomerDTO customerDTO) {
        Customer customer = getCustomer(id);
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customer.setTckn(customerDTO.getTckn());
        customer.setBirthDate(String.valueOf(customerDTO.getBirthDate()));
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setSalary(customerDTO.getSalary());
        customer.setGuaranteeAmount(customerDTO.getGuaranteeAmount());
        if (Objects.nonNull(customer.getGuaranteeAmount()) && customer.getGuaranteeAmount().compareTo(BigDecimal.ZERO) > 0) {
            customer.setGuarantee(true);
        }
        return customer;
    }

    @Override
    public Customer getCustomerByTcknAndStatus(String tckn, Status status) {
        return customerRepository.findByTckn(tckn).orElseThrow(() -> new NotFoundObject("Customer Not Found"));
    }

    private Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundObject("Customer Not Found"));
    }
}