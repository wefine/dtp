package org.wefine.dtx.chain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wefine.dtx.chain.dao.CustomerRepository;

import org.wefine.dtx.chain.entity.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional(rollbackFor = Exception.class)
    @JmsListener(destination = "customer:msg:new")
    public void handle(String msg) {
        log.info("Get msg1:{}", msg);
        Customer customer = new Customer();
        customer.setUsername(msg);
        customer.setDeposit(100);
        customerRepository.save(customer);
        if (msg.contains("error1")) {
            throw new RuntimeException("Error1");
        }

        jmsTemplate.convertAndSend("customer:msg:reply", msg + " created.");
        if (msg.contains("error2")) {
            throw new RuntimeException("Error2");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Customer create(Customer customer) {
        log.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        customer = customerRepository.save(customer);
        if (customer.getUsername().contains("error1")) {
            throw new RuntimeException("Error1");
        }
        jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
        if (customer.getUsername().contains("error2")) {
            throw new RuntimeException("Error2");
        }

        return customer;
    }

}
