package org.wefine.dtx.chain.web;

import org.wefine.dtx.chain.dao.CustomerRepository;
import org.wefine.dtx.chain.entity.Customer;
import org.wefine.dtx.chain.service.CustomerService;
import org.wefine.dtx.chain.service.CustomerServiceTxInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping("/msg")
    public void create(@RequestBody Customer customer) {
        jmsTemplate.convertAndSend("customer:msg:new", customer.getUsername());
    }

    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        return customerServiceInCode.create(customer);
    }

    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/msg")
    public String getMsg() {
        jmsTemplate.setReceiveTimeout(3000);
        Object reply = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(reply);
    }
}
