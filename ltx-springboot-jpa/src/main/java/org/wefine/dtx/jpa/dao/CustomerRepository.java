package org.wefine.dtx.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wefine.dtx.jpa.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findOneByUsername(String username);
}
