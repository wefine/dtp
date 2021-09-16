package org.wefine.dtx.chain.dao;

import org.wefine.dtx.chain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
