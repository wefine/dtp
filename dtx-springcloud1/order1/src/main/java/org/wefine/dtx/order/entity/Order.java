package org.wefine.dtx.order.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String detail;
    private int amount;
}
