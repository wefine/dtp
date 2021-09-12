package org.wefine.dtx.jpa.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wefine.dtx.jpa.dao.CustomerRepository;
import org.wefine.dtx.jpa.entity.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceTxInAnnotation {
    @Resource
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer) {
        log.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        return customerRepository.save(customer);
    }
}
