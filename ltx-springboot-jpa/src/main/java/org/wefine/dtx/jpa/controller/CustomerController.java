package org.wefine.dtx.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wefine.dtx.jpa.dao.CustomerRepository;
import org.wefine.dtx.jpa.entity.Customer;
import org.wefine.dtx.jpa.service.CustomerServiceTxInAnnotation;
import org.wefine.dtx.jpa.service.CustomerServiceTxInCode;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceTxInAnnotation customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        log.info("CustomerResource create in annotation create customer:{}", customer.getUsername());
        return customerService.create(customer);
    }

    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        log.info("CustomerResource create in code create customer:{}", customer.getUsername());
        return customerServiceInCode.create(customer);
    }

    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
