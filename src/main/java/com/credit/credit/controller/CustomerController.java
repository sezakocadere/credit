package com.credit.credit.controller;

import com.credit.credit.model.Customer;
import com.credit.credit.model.CustomerDTO;
import com.credit.credit.model.RequestCustomerDTO;
import com.credit.credit.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService costumerService;

    @GetMapping
    public List<Customer> listCostumer() {
        return costumerService.getAllCustomer();
    }

    @PostMapping
    public CustomerDTO postCostumer(@RequestBody RequestCustomerDTO request) {
        return costumerService.createCustomer(request).toDTO();
    }

    @PutMapping(value = "/{customerId}")
    public CustomerDTO putCostumer(
            @PathVariable("customerId") Long id, @RequestBody RequestCustomerDTO request) {
        return costumerService.updateCustomer(id, request).toDTO();
    }

    @DeleteMapping(value = "/{customerId}")
    public void deleteCostumer(
            @PathVariable("customerId") Long id) {
        costumerService.removeCustomer(id);
    }
}
