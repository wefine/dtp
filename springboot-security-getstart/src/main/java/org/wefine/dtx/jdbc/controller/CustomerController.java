package org.wefine.dtx.jdbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.wefine.dtx.jdbc.dao.CustomerRepository;
import org.wefine.dtx.jdbc.entity.Customer;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("")
    @Secured("ROLE_ADMIN")
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
