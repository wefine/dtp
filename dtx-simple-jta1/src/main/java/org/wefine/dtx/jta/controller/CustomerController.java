package org.wefine.dtx.jta.controller;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import org.wefine.dtx.jta.dao.CustomerRepository;
import org.wefine.dtx.jta.entity.Customer;
import org.wefine.dtx.jta.service.CustomerServiceTxInAnnotation;
import org.wefine.dtx.jta.service.CustomerServiceTxInCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Resource
    private CustomerServiceTxInAnnotation customerService;
    @Resource
    private CustomerServiceTxInCode customerServiceInCode;
    @Resource
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
