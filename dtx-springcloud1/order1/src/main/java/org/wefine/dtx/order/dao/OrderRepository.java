package org.wefine.dtx.order.dao;

import org.wefine.dtx.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOneByTitle(String title);
}
