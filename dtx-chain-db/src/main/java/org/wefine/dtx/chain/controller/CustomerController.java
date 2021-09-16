package org.wefine.dtx.chain.controller;

import org.wefine.dtx.chain.domain.Order;
import org.wefine.dtx.chain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/order")
    public void create(@RequestBody Order order) {
        customerService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Map userInfo(@PathVariable Long id) {
        return customerService.userInfo(id);
    }

}
