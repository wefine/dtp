package org.wefine.dtx.jdbc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wefine.dtx.jdbc.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
