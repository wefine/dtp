package org.wefine.dtx.chain.service;

import org.wefine.dtx.chain.dao.CustomerRepository;
import org.wefine.dtx.chain.entity.Customer;
import org.wefine.dtx.chain.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    private static final String SQL_CREATE_ORDER = "INSERT INTO customer_order (customer_id, title, amount) VALUES (?, ?, ?)";

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order) {
        Customer customer = customerRepository.getOne(order.getCustomerId());
        customer.setDeposit(customer.getDeposit() - order.getAmount());
        customerRepository.save(customer);
        if (order.getTitle().contains("error1")) {
            throw new RuntimeException("Error1");
        }
        orderJdbcTemplate.update(SQL_CREATE_ORDER, order.getCustomerId(), order.getTitle(), order.getAmount());

        if (order.getTitle().contains("error2")) {
            throw new RuntimeException("Error2");
        }
    }


    public Map userInfo(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List orders = orderJdbcTemplate.queryForList("SELECT * from customer_order where customer_id = " + customerId);

        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        return result;
    }

}
